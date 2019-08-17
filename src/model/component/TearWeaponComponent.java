package model.component;

import com.google.common.eventbus.Subscribe;

import model.component.mentality.AbstractMentalityComponent;
import model.entity.Entity;
import model.entity.Tear;
import model.enumeration.TearEnum;
import model.events.TearShotEvent;
import model.util.Position;
import util.EventListener;

/**
 * 
 * This is the component that generates tears when an entity attacks.
 *
 */

public class TearWeaponComponent extends AbstractComponent {
    private static final double DEFAULT_LIFE_TIME = 1000;
    private static final double DEFAULT_SPEED = 10.0;
    private double damage;
    private double lifeTime;
    private double time;
    private final TearEnum nameTear;

    /**
     * Basic constructor that generates a tear when requested.
     * 
     * @param entity   to which this component is attached
     * @param nameTear enumeration used to display it.
     * @param damage   the damage caused.
     * @param tearRate the rate of fire.
     */
    public TearWeaponComponent(final Entity entity, final double damage, final TearEnum nameTear,
            final double tearRate) {
        super(entity);
        this.damage = damage;
        this.lifeTime = DEFAULT_LIFE_TIME;
        this.nameTear = nameTear;
        time = tearRate;
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
        final Position pos = event.getSourceEntity().getComponent(BodyComponent.class).get().getPosition();
        final double width = event.getSourceEntity().getComponent(BodyComponent.class).get().getWidth();
        final double height = event.getSourceEntity().getComponent(BodyComponent.class).get().getHeight();
        getEntity().getRoom()
                .insertEntity(new Tear(event.getAngle(),
                        new Position(pos.getX() + width / 2, pos.getY() + height / 2, pos.getZ()),
                        event.getSourceEntity().getComponent(MoveComponent.class).get().getMovement(), this.damage,
                        this.lifeTime, DEFAULT_SPEED, this.nameTear,
                        event.getSourceEntity().getComponent(AbstractMentalityComponent.class).get()));
    }

    /**
     * 
     * @return damage of tear.
     */
    public double getDamage() {
        return damage;
    }

    /**
     * 
     * @param damage of tear.
     */
    public void setDamage(final double damage) {
        this.damage = damage;
    }

    /**
     * 
     * @return life time of tear.
     */
    public double getLifeTime() {
        return lifeTime;
    }

    /**
     * 
     * @param lifeTime time of tear.
     */
    public void setLifeTime(final double lifeTime) {
        this.lifeTime = lifeTime;
    }
}
