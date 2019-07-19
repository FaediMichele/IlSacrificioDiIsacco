package model.component.collision;

import model.component.collectible.AbstractPickupableComponent;
import model.entity.Entity;
import model.events.CollisionEvent;
import model.events.PickUpEvent;

/**
 * Collision component of the player.
 *
 */
public class PlayerCollisionComponent extends MovableCollisionComponent {

    /**
     * Default CollisionComponent constructor.
     * 
     * @param entity entity for this component
     */
    public PlayerCollisionComponent(final Entity entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleCollision(final CollisionEvent event) {
        super.handleCollision(event);
        this.collectibleManagement(event);
    }

    /**
     * This method is called when the entity collides with entities and must manage
     * ONLY if this entity must be collected or not.
     * 
     * @param event is the collision event
     */
    protected void collectibleManagement(final CollisionEvent event) {
        if (event.getSourceEntity().hasComponent(AbstractPickupableComponent.class)) {
            getEntity().postEvent(new PickUpEvent(event.getSourceEntity()));
        }
    }
}
