package model.component.collision;

import java.util.List;

import model.component.BodyComponent;
import model.component.MoveComponent;
import model.component.collectible.AbstractPickupableComponent;
import model.entity.AbstractStaticEntity;
import model.entity.Entity;
import model.events.CollisionEvent;
import util.EventListener;
import util.Pair;
import util.StaticMethodsUtils;

/**
 * 
 * This is the component of the collisions of all the entities that move.
 *
 */
public class MovableCollisionComponent extends CollisionComponent {
    /**
     * Default CollisionComponent constructor.
     * 
     * @param entity entity for this component
     */
    public MovableCollisionComponent(final Entity entity) {
        super(entity);
    }

    /**
     * Custom event Listener.
     * 
     * @param entity         the {@link Entity}
     * @param eventListeners the {@link EventListener}
     */
    public MovableCollisionComponent(final Entity entity, final List<EventListener<CollisionEvent>> eventListeners) {
        super(entity, eventListeners);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleCollision(final CollisionEvent event) {
        super.handleCollision(event);
        if (event.getSourceEntity().hasComponent(AbstractPickupableComponent.class)) {
            return;
        }
        this.handleMovement(event);
    }

    /**
     * Handles the movement of the entities after collision.
     * 
     * @param event the {@link Event}
     */
    protected void handleMovement(final CollisionEvent event) {
        getBodyComponent(this.getEntity());
        BodyComponent b1 = event.getSourceEntity().getComponent(BodyComponent.class).get();
        BodyComponent b2 = this.getEntity().getComponent(BodyComponent.class).get();
        if (event.getSourceEntity() instanceof AbstractStaticEntity
                && !(touchUp(b1, b2) && touchLeft(b1, b2) || touchUp(b1, b2) && touchRight(b1, b2) || touchDown(b1, b2) && touchLeft(b1, b2) || touchDown(b1, b2) && touchRight(b1, b2))) {
            if (touchUp(b1, b2)) {
                getMoveComponent(this.getEntity()).move(270);
                return;
            } else if (touchDown(b1, b2)) {
                getMoveComponent(this.getEntity()).move(90);
                return;
            } else if (touchLeft(b1, b2)) {
                getMoveComponent(this.getEntity()).move(0);
                return;
            } else if (touchRight(b1, b2)) {
                getMoveComponent(this.getEntity()).move(180);
                return;
                // l = new Line(new Pair<Double, Double>(b1.getPosition().getX() + b1.getWidth(), b1.getPosition().getY()), new Pair<Double, Double>(b1.getPosition().getX() + b1.getWidth(), b1.getPosition().getY() + b1.getHeight()));
            }
            /*l.perpendicular(new Pair<Double, Double>(b2.getPosition().getX() + b2.getWidth() / 2, b2.getPosition().getY() + b2.getHeight() / 2));
            getMoveComponent(this.getEntity()).move(l.getAngle());
            return;*/
        }
        Pair<Double, Double> p1 = new Pair<>(b1.getPosition().getX() + b1.getWidth() / 2, b1.getPosition().getY() + b1.getHeight() / 2);
        Pair<Double, Double> p2 = new Pair<>(b2.getPosition().getX(), b2.getPosition().getY());
        getMoveComponent(this.getEntity()).move(StaticMethodsUtils.getAngle(p2, p1));
    }
    private boolean touchUp(final BodyComponent b1, final BodyComponent b2) {
        return (b1.getPosition().getX() < b2.getPosition().getX()
                && b1.getPosition().getX() + b1.getWidth() > b2.getPosition().getX() + b2.getWidth() && b1.getPosition().getY() > b2.getPosition().getY()
                && b1.getPosition().getY() < b2.getPosition().getY() + b2.getHeight()); 
    }
    private boolean touchRight(final BodyComponent b1, final BodyComponent b2) {
        return b1.getPosition().getX() + b1.getWidth() > b2.getPosition().getX()
                && b1.getPosition().getX() + b1.getWidth() < b2.getPosition().getX() + b2.getWidth() && (b1.getPosition().getY() < b2.getPosition().getY()
                        && b1.getPosition().getY() + b1.getHeight() > b2.getPosition().getY());
    }
    private boolean touchDown(final BodyComponent b1, final BodyComponent b2) {
        return (b1.getPosition().getX() < b2.getPosition().getX()
                && b1.getPosition().getX() + b1.getWidth() > b2.getPosition().getX() + b2.getWidth()
                && b1.getPosition().getY() + b1.getHeight() > b2.getPosition().getY() && b1.getPosition().getY() + b1.getHeight() < b2.getPosition().getY() + b2.getHeight());
    }
    private boolean touchLeft(final BodyComponent b1, final BodyComponent b2) {
        return b1.getPosition().getX() > b2.getPosition().getX() 
                && b1.getPosition().getX() < b2.getPosition().getX() + b2.getWeight() 
                && (b1.getPosition().getY() < b2.getPosition().getY()
                        && b1.getPosition().getY() + b1.getHeight() > b2.getPosition().getY() + b2.getHeight());
    }

    private MoveComponent getMoveComponent(final Entity e) {
        if (e.getComponent(MoveComponent.class).isPresent()) {
            return e.getComponent(MoveComponent.class).get();
        } else {
            throw new IllegalStateException();
        }
    }

    private BodyComponent getBodyComponent(final Entity e) {
        if (e.getComponent(BodyComponent.class).isPresent()) {
            return e.getComponent(BodyComponent.class).get();
        } else {
            throw new IllegalStateException();
        }
    }

}
