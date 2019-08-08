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
    private static final double DEFAULT_DAMAGE = 0.5;
    /**
     * Basic constructor that generates a tear when requested.
     * @param entity to which this component is attached
     * @param damage the damage the Tear will cause
     */
    public TearWeaponComponent(final Entity entity, final double damage) {
        super(entity);
        this.registerListener(new EventListener<TearShotEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final TearShotEvent event) {
                final Tear t = new Tear(event.getAngle(), event.getSourceEntity(), damage);
                System.out.println(t);
                System.out.println(getEntity());
                System.out.println(getEntity().getNameEntity());
                getEntity().getRoom().insertEntity(t);
            }
        });
    }

    /**
     * Basic constructor that generates a tear when requested.
     * @param entity to which this component is attached
     */
    public TearWeaponComponent(final Entity entity) {
        this(entity, DEFAULT_DAMAGE);
    }
}
