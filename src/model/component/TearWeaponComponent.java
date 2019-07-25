package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.entity.Tear;
import model.events.TearShotEvent;
import util.EventListener;

/**
 * 
 * This is the component that generates tears when an entity attacks.
 *
 */

public class TearWeaponComponent extends AbstractComponent<TearWeaponComponent> {

    /**
     * Basic constructor that generates a tear when requested.
     * @param entity to which this component is attached
     */
    public TearWeaponComponent(final Entity entity) {
        super(entity);
        this.registerListener(new EventListener<TearShotEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final TearShotEvent event) {
                final Tear t = new Tear(event.getAngle(), event.getSourceEntity());
                getEntity().getRoom().insertEntity(t);
                //t.getStatusComponent().setStatus(new Pair<>(1, "appear"));
            }
        });
    }
}
