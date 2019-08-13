package model.component.collision;

import model.component.collectible.AbstractPickupableComponent;
import model.entity.Entity;
import model.enumeration.BasicStatusEnum;
import model.events.CollisionEvent;
import model.events.PickUpEvent;

/**
 * Collision component of the player.
 *
 */
public class PlayerCollisionComponent extends MovableCollisionComponent {

    private static final double WAIT_TIME = 500;
    private double time;

    /**
     * Default CollisionComponent constructor.
     * 
     * @param entity entity for this component
     */
    public PlayerCollisionComponent(final Entity entity) {
        super(entity);
        time = 0;
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
     * {@inheritDoc}
     * If the player as been damaged recently, sets the status as already damaging and does not damage his life again.
     */
    @Override
    protected void damage(final CollisionEvent event) {
        if (time >= WAIT_TIME) {
            super.damage(event);
        } else {
            this.getEntity().getStatusComponent().setStatus(BasicStatusEnum.DAMAGING);
        }
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Double deltaTime) {
        this.time += deltaTime;
    }
}
