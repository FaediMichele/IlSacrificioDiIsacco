package model.component;

import com.google.common.eventbus.Subscribe;

import model.component.mentality.AbstractMentalityComponent;
import model.entity.Entity;
import model.entity.Tear;
import model.enumeration.EntityEnum;
import model.events.TearShotEvent;
import util.EventListener;

/**
 * 
 * This is the component that generates tears when an entity attacks.
 *
 */

public class TearWeaponComponent extends AbstractComponent<TearWeaponComponent> {
    private static final double DEFAULT_LIFE_TIME = 10000;
    private static final double DEFAULT_SPEED = 10;
    private final double damage;
    private final double lifeTime;
    private final EntityEnum nameTear;
    /**
     * Basic constructor that generates a tear when requested.
     * @param entity to which this component is attached
     * @param nameTear .
     * @param damage .
     * 
     */
    public TearWeaponComponent(final Entity entity, final double damage, final EntityEnum nameTear) {
        super(entity);
        this.damage = damage;
        this.lifeTime = DEFAULT_LIFE_TIME;
        this.nameTear = nameTear;
        this.registerListener(new EventListener<TearShotEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final TearShotEvent event) {
                tearShotEvent(event);
            }
        });
    }

    private void tearShotEvent(final TearShotEvent event) {
        getEntity().getRoom().insertEntity(
                new Tear(event.getAngle(),
                         event.getSourceEntity().getComponent(BodyComponent.class).get().getPosition(), 
                         this.damage, 
                         this.lifeTime, 
                         DEFAULT_SPEED, 
                         this.nameTear, 
                         event.getSourceEntity().getComponent(AbstractMentalityComponent.class).get())
                );
    }

//    /**
//     * Basic constructor that generates a tear when requested.
//     * @param entity to which this component is attached
//     */
//    public TearWeaponComponent(final Entity entity) {
//        this(entity, DEFAULT_DAMAGE);
//    }
}
