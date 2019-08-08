package model.component.collision;

import java.util.List;
import model.component.BodyComponent;
import model.component.MoveComponent;
import model.entity.Entity;
import model.events.CollisionEvent;
import model.events.MoveEvent;
import util.EventListener;

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
        this.handleMovement(event);
    }

    /**
     * Handles the movement of the entities after collision.
     * 
     * @param event the {@link Event}
     */
    protected void handleMovement(final CollisionEvent event) {
        getBodyComponent(this.getEntity()).goPrevious();
        getMoveComponent(this.getEntity()).stop();
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
