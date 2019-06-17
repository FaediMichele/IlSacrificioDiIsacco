package model.component;

import model.entity.Entity;
import model.entity.Tear;
import model.events.EventListener;
import model.events.TearShotEvent;
import util.Pair;

/**
 * 
 * This is the component that generates tears when Isacco attacks.
 *
 */

public class TearWeaponComponent extends AbstractComponent<TearWeaponComponent> {

    TearWeaponComponent(final Entity entity) {
        super(entity);
        registerListeners();
    }

    TearWeaponComponent(final Entity entity, final TearWeaponComponent component) {
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
            }
        });
    }

}
