package model.entity;

import com.google.common.eventbus.Subscribe;
import model.component.InventoryComponent;
import model.component.LockComponent;
import model.events.CollisionEvent;
import util.EventListener;

/**
 * 
 * Simple entity closed by a padlock.
 *
 */
public class SimpleLockEntity extends AbstractStaticEntity {

    SimpleLockEntity() {
        this.attachComponent(new LockComponent(this));
        this.registerListener(new EventListener<CollisionEvent>() {

            @Subscribe
            @Override
            public void listenEvent(final CollisionEvent event) {
                if (event.getSourceEntity().getClass().isInstance(Player.class)) {
                    InventoryComponent invcmp = (InventoryComponent) event.getSourceEntity()
                                                    .getComponent(InventoryComponent.class).get();
                    if (invcmp.thingsOfThisKind(Key.class) > 0) {
                        LockComponent lc = (LockComponent) getComponent(LockComponent.class).get();
                        lc.unlock();
                    }
                }
            }
        });
    }
}
