package model.component.collision;

import com.google.common.eventbus.Subscribe;

import model.component.InventoryComponent;
import model.entity.Entity;
import model.entity.Key;
import model.entity.Player;
import model.events.CollisionEvent;
import model.events.UseThingEvent;
import util.EventListener;

/**
 * 
 * Collision component for entity locked.
 *
 */
public class LockCollisionComponent extends CollisionComponent {
    private  boolean isLock;
    /**
     * 
     * @param entity is {@link Entity} to which the component belongs.
     */
    public LockCollisionComponent(final Entity entity) {
        super(entity);
        this.isLock = true;
        this.registerListener(new EventListener<CollisionEvent>() {

            @Subscribe
            @Override
            public void listenEvent(final CollisionEvent event) {
                if (event.getSourceEntity().getClass().isInstance(Player.class)) {
                    InventoryComponent invcmp = (InventoryComponent) event.getSourceEntity()
                            .getComponent(InventoryComponent.class).get();

                    if (invcmp.thingsOfThisKind(Key.class) > 0 && isLock) {
                        event.getSourceEntity().postEvent(new UseThingEvent(event.getSourceEntity(), Key.class));
                        isLock = false;
                    }
                }
            }
        });
    }
    /**
     * 
     * @return true if entity is locked false otherwise.
     */
    public boolean isLocked() {
        return isLock;
    }
}
