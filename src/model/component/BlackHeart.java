package model.component;

import model.component.mentality.AbstractMentalityComponent;
import model.component.mentality.EnemyMentalityComponent;
import model.entity.Entity;
import model.enumeration.BasicHeartEnum;
import model.enumeration.HeartEnum;
import model.events.DamageEvent;

/**
 * When a black heart value reaches 0, it damages all the enemy in the room.
 * Black heart uses the builder pattern to create itself.
 */

public class BlackHeart extends SimpleHeart {
    private static final double ENEMY_DAMAGE = 0.5;

    /**
     * @param myEntity the entity to which it is attached
     * @param value actual value of the heart
     */
    public BlackHeart(final Entity myEntity, final double value) {
        super(myEntity, value);
    }

    /**
     * 
     * @param myEntity the entity to which it is attached
     */
    public BlackHeart(final Entity myEntity) {
        super(myEntity);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void died() {
        super.getMyEntity().getRoom().getEntities().stream()
        .filter(i -> i.hasComponent(AbstractMentalityComponent.class))
        .filter(i -> i.getComponent(EnemyMentalityComponent.class).isPresent())
        .forEach(i -> i.postEvent(new DamageEvent(super.getMyEntity(), ENEMY_DAMAGE)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HeartEnum getColor() {
        return BasicHeartEnum.BLACK;
    }
}
