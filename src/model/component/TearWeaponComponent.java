package model.component;

import com.google.common.eventbus.Subscribe;

import model.component.mentality.AbstractMentalityComponent;
import model.component.mentality.PlayerTearsMentalityComponent;
import model.entity.Entity;
import model.entity.Player;
import model.entity.Tear;
import model.enumeration.EntityEnum;
import model.events.TearShotEvent;
import util.EventListener;
import util.Triplet;

/**
 * 
 * This is the component that generates tears when an entity attacks.
 *
 */

public class TearWeaponComponent extends AbstractComponent<TearWeaponComponent> {
    private static final double DEFAULT_LIFE_TIME = 1000;
    private static final double DEFAULT_SPEED = 10.0;
    private final double tearRate = 100;
    private double damage;
    private double lifeTime;
    private double time = tearRate;
    private final EntityEnum nameTear;
    /**
     * Basic constructor that generates a tear when requested.
     * @param entity to which this component is attached
     * @param nameTear enum used to display it.
     * @param damage the damage caused.
     * @param tearRate the rate of fire.
     */
    public TearWeaponComponent(final Entity entity, final double damage, final EntityEnum nameTear, final double tearRate) {
        super(entity);
        this.damage = damage;
        this.lifeTime = DEFAULT_LIFE_TIME;
        this.nameTear = nameTear;
        this.registerListener(new EventListener<TearShotEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final TearShotEvent event) {
                if (time >= tearRate) {
                    tearShotEvent(event);
                    time = 0;
                }
            }
        });
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void update(final Double deltaTime) {
        super.update(deltaTime);
        time += deltaTime;
    }

    private void tearShotEvent(final TearShotEvent event) {
        getEntity().getRoom().insertEntity(
                new Tear(event.getAngle(),
                         event.getSourceEntity().getComponent(BodyComponent.class).get().getPosition(), 
                         event.getSourceEntity().getComponent(MoveComponent.class).get().getMovement(),
                         this.damage, this.lifeTime, DEFAULT_SPEED, this.nameTear, 
                         event.getSourceEntity() instanceof Player
                                 ? new PlayerTearsMentalityComponent(this.getEntity())
                                 : event.getSourceEntity().getComponent(AbstractMentalityComponent.class).get())
                );
    }

    /**
     * 
     * @return .
     */
    public double getDamage() {
        return damage;
    }

    /**
     * 
     * @param damage 
     */ 
    public void setDamage(final double damage) {
        this.damage = damage;
    }

    /**
     * 
     * @return .
     */
    public double getLifeTime() {
        return lifeTime;
    }

    /**
     * 
     * @param lifeTime .
     */
    public void setLifeTime(final double lifeTime) {
        this.lifeTime = lifeTime;
    }
}
