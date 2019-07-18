package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.entity.Tear;
import model.events.TearShotEvent;
import util.EventListener;
import util.Pair;

/**
 * 
 * This is the component that generates tears when an entity attacks.
 *
 */

public class TearWeaponComponent extends AbstractComponent<TearWeaponComponent> {

    private static final long DEFAULT_TEAR_LIFETIME = 2000000;
    /**
     * Basic constructor that generates a tear when requested.
     * @param entity to which this component is attached
     * @param lifetime of the tear
     */
    public TearWeaponComponent(final Entity entity, final long lifetime) {
        super(entity);
        this.registerListener(new EventListener<TearShotEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final TearShotEvent event) {
                final Tear t = new Tear(event.getAngle(), event.getSourceEntity());
                getEntity().getRoom().insertEntity(t);
                t.getStatusComponent().setStatus(new Pair<>(1, "appear"));
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(lifetime);
                            getEntity().getRoom().deleteEntity(t);
                            t.getStatusComponent().setStatus(new Pair<>(1, "disappear"));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    /**
     * Constructor that generates a tear when requested, with a default lifetime.
     * @param entity to which this component is attached
     */
    public TearWeaponComponent(final Entity entity) {
        this(entity, DEFAULT_TEAR_LIFETIME);
    }
}
