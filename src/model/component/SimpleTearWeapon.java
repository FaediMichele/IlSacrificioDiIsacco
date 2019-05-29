package model.component;

import model.entity.Entity;
import model.entity.Tear;
import model.events.EventListener;
import model.events.TearShotEvent;

/**
 * 
 * It is the component that generates tears when Isacco attacks.
 *
 */

public class SimpleTearWeapon extends AbstractComponent<SimpleTearWeapon> {

    SimpleTearWeapon(final Entity entity) {
        super(entity);
        registerListeners();
    }

    SimpleTearWeapon(final Entity entity, final SimpleTearWeapon component) {
        super(entity, component);
        registerListeners();
    }

    private void registerListeners() {
        this.registerListener(new EventListener<TearShotEvent>() {
            @Override
            public void listenEvent(final TearShotEvent event) {
                Tear t = new Tear(event.getDirection(), event.getSourceEntity());
                getEntity().getRoom().insertEntity(t);
                while (getEntity().getRoom().getEntity().contains(t)) {
                    ((TearComponent) t.getComponent(TearComponent.class).get()).move();
                    try {
                        Thread.sleep((long) (((MoveComponent) t.getComponent(MoveComponent.class).get()).getSpeed() * 100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
