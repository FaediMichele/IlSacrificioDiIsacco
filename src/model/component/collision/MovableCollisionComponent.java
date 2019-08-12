package model.component.collision;

import java.util.List;
import java.util.Optional;

import model.component.BodyComponent;
import model.component.MoveComponent;
import model.component.collectible.AbstractPickupableComponent;
import model.component.mentality.AbstractMentalityComponent;
import model.entity.AbstractStaticEntity;
import model.entity.Entity;
import model.events.CollisionEvent;
import model.util.Position;
import util.EventListener;
import util.Pair;
import util.StaticMethodsUtils;

/**
 * 
 * This is the component of the collisions of all the entities that move.
 *
 */
public class MovableCollisionComponent extends CollisionComponent {

    private static final double COLLISION_ERROR = -1; /* this is very important, so touching Entity sees the contact */

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
        final Optional<AbstractMentalityComponent> me = this.getEntity().getComponent(AbstractMentalityComponent.class);
        final Optional<AbstractMentalityComponent> other = event.getSourceEntity().getComponent(AbstractMentalityComponent.class);
        if ((!me.isPresent() && !other.isPresent()) || (me.isPresent() && other.isPresent() && (me.get().canCollide(other.get().getClass()) && other.get().canCollide(me.get().getClass())))) {
            this.handleMovement(event);
        }
    }

    /**
     * Handles the movement of the entities after collision.
     * 
     * @param event the {@link Event}
     */
    protected void handleMovement(final CollisionEvent event) {
        if (event.getSourceEntity() instanceof AbstractStaticEntity) {
            reactToCollision(event.getSourceEntity());
            return;
        }
        final BodyComponent b1 = event.getSourceEntity().getComponent(BodyComponent.class).get();
        final BodyComponent b2 = this.getEntity().getComponent(BodyComponent.class).get();
        final Pair<Double, Double> p1 = new Pair<>(b1.getPosition().getX() + b1.getWidth() / 2, b1.getPosition().getY() + b1.getHeight() / 2);
        final Pair<Double, Double> p2 = new Pair<>(b2.getPosition().getX() + b2.getWidth() / 2, b2.getPosition().getY() + b2.getHeight() / 2);
        getMoveComponent(this.getEntity()).move(StaticMethodsUtils.getAngle(p2, p1));
    }

    private void reactToCollision(final Entity other) {
        final BodyComponent mine = getBodyComponent(getEntity());
        final BodyComponent otherBody = getBodyComponent(other);
        final MoveComponent mySpeed = getEntity().getComponent(MoveComponent.class).get();
            Double newX = mine.getPosition().getX();
            Double newY = mine.getPosition().getY();
            final Double initVelX = mySpeed.getMovement().getX();
            final Double initVelY = mySpeed.getMovement().getY();
            if (touchDown(mine, otherBody) && touchRight(mine, otherBody)) {
                    if (mine.getPosition().getX() + mine.getWidth() - otherBody.getPosition().getX() - initVelX
                            < mine.getPosition().getY() + mine.getHeight() - otherBody.getPosition().getY() - initVelY) {
                            newX = rectifyRight(mine, otherBody);
                    } else {
                            newY = rectifyDown(mine, otherBody);
                    }
            } else if (touchDown(mine, otherBody) && touchLeft(mine, otherBody)) {
                    if (mine.getPosition().getY() + mine.getHeight() - otherBody.getPosition().getY() - initVelY
                            > otherBody.getPosition().getX() + otherBody.getWidth() - mine.getPosition().getX() - initVelX) {
                            newX = rectifyLeft(otherBody);
                    } else {
                            newY = rectifyDown(mine, otherBody);
                    }
            } else if (touchUp(mine, otherBody) && touchRight(mine, otherBody)) {
                    if (mine.getPosition().getX() + mine.getWidth() - otherBody.getPosition().getX() + initVelX
                            < otherBody.getPosition().getY() + otherBody.getHeight() - mine.getPosition().getY() + initVelY) {
                            newX = rectifyRight(mine, otherBody);
                    } else {
                            newY = rectifyUp(otherBody);
                    }
            } else if (touchUp(mine, otherBody) && touchLeft(mine, otherBody)) {
                    if (otherBody.getPosition().getY() + otherBody.getHeight() - mine.getPosition().getY() + initVelY
                            > otherBody.getPosition().getX() + otherBody.getWidth() - mine.getPosition().getX() + initVelX) {
                            newX = rectifyLeft(otherBody);
                    } else {
                            newY = rectifyUp(otherBody);
                    }
            } else if (touchDown(mine, otherBody)) {
                    newY = rectifyDown(mine, otherBody);
            } else if (touchLeft(mine, otherBody)) {
                    newX = rectifyLeft(otherBody);
            } else if (touchRight(mine, otherBody)) {
                    newX = rectifyRight(mine, otherBody);
            } else if (touchUp(mine, otherBody)) {
                    newY = rectifyUp(otherBody);
            }
    mine.setPosition(new Position(newX, newY, mine.getPosition().getZ()));
    }

    private double rectifyRight(final BodyComponent me, final BodyComponent other) {
            return other.getPosition().getX() - me.getWidth() + COLLISION_ERROR;
    }
    private double rectifyLeft(final BodyComponent other) {
            return other.getPosition().getX() + other.getWidth() - COLLISION_ERROR;
    }
    private double rectifyDown(final BodyComponent me, final BodyComponent other) {
            return other.getPosition().getY() + COLLISION_ERROR - me.getHeight();
    }
    private double rectifyUp(final BodyComponent other) {
            return other.getPosition().getY() + other.getHeight() - COLLISION_ERROR;
    }
    private boolean touchDown(final BodyComponent me, final BodyComponent other) {
            return me.getPosition().getY() < other.getPosition().getY()
                    && me.getPosition().getY() + me.getHeight() > other.getPosition().getX();
    }
    private boolean touchLeft(final BodyComponent me, final BodyComponent other) {
            return me.getPosition().getX() < other.getPosition().getX() + other.getWidth()
            && me.getPosition().getX() + me.getWidth() > other.getPosition().getX() + other.getWidth();
    }
    private boolean touchRight(final BodyComponent me, final BodyComponent other) {
            return me.getPosition().getX() < other.getPosition().getX()
                    && me.getPosition().getX() + me.getWidth() > other.getPosition().getX();
    }
    private boolean touchUp(final BodyComponent me, final BodyComponent other) {
            return me.getPosition().getY() < other.getPosition().getY() + other.getHeight()
            && me.getPosition().getY() + me.getHeight() > other.getPosition().getY() + other.getHeight();
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
