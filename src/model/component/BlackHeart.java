package model.component;

import model.component.mentality.AbstractMentalityComponent;
import model.component.mentality.EnemyMentalityComponent;
import model.entity.Entity;
import model.enumeration.BasicColorEnum;
import model.enumeration.ColorHeartEnum;
import model.events.DamageEvent;

/**
 * When a black heart value reaches 0, it damages all the enemy in the room.
 * Black heart uses the builder pattern to create itself.
 */

public class BlackHeart extends SimpleHeart {

    public BlackHeart(final Entity myEntity, final double value) {
        super(myEntity, value);
    }

    private static final double ENEMY_DAMAGE = 0.5;
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
    public ColorHeartEnum getColor() {
        return BasicColorEnum.BLACK;
    }
}
