package model.component;

import model.entity.Entity;
import model.events.CollisionEvent;
import model.events.PickUpEvent;

/**
 * Collision component of the player.
 *
 */
public class ComponentCollisionsPlayer extends MovableCollisionComponent {

    /**
     * Default CollisionComponent constructor.
     * 
     * @param entity entity for this component
     */
    public ComponentCollisionsPlayer(final Entity entity) {
        super(entity);
//                /**
//                 * handles the disappearing of the tear after a collision
//                 */
//                if (getEntity().getClass().equals(Tear.class)) {
//                    getEntity().getRoom().deleteEntity(getEntity());
//                }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleCollision(final CollisionEvent event) {
        super.handleCollision(event);
        this.collectibleManagement(event);
        this.lockedManagement(event);
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
     * This method is called when this entity collides with another entity, this
     * method manages ONLY the opening or not of the locked components.
     * 
     * @param event is the collision event
     */
    protected void lockedManagement(final CollisionEvent event) {
//      if (event.getSourceEntity().hasComponent(LockComponent.class)) {
//      /**
//       * la porta è chiusa?
//       */
//
//      /**
//       * ho la chiave
//       */
//      if (getEntity().hasComponent(InventoryComponent.class) &&
//              ((InventoryComponent)getEntity().getComponent(InventoryComponent.class).get()).thingsOfThisKind(Key.class))  {
//          if (((KeychainComponent) getEntity().getComponent(KeychainComponent.class).get())
//                  .getKey()
//                  .contains(event.getSourceEntity())) {
//          }
//      }
//  }
    }

}
