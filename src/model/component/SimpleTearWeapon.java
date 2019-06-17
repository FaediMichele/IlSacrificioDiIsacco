package model.component;

import model.entity.Entity;
import model.entity.Tear;
import model.events.EventListener;
import model.events.TearShotEvent;
import util.Pair;

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
                final Tear t = new Tear(event.getAngle(), event.getSourceEntity());
                getEntity().getRoom().insertEntity(t);
                t.getStatusComponent().setStatus(new Pair<>(1, "appear"));
                while (getEntity().getRoom().getEntity().contains(t)) {
                    ((TearAIComponent) t.getComponent(TearAIComponent.class).get()).move();
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
