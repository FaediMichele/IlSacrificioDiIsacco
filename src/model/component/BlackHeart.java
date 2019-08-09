package model.component;

import java.util.Objects;
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

    private final Entity myEntity;
    private final double enemyDamage;

    /**
     * 
     * @param myEntity          entity is needed when you create a black heart;
     * @param enemyDamage       value of the damage to the enemies
     * @param heartValue        value of the heart
     */
    protected BlackHeart(final Entity myEntity, final double enemyDamage, final double heartValue) {
        super(heartValue);
        this.enemyDamage = enemyDamage;
        this.myEntity = myEntity;
    }

    /**
     * Builder for BlackHeart.
     */
    public static class Builder {
        private static final double DEFAULT_ENEMY_DAMAGE = 0.3;
        private static final double DEFAULT_HEART_VALUE = 1;
        private final Entity e;
        private double damage = DEFAULT_ENEMY_DAMAGE;
        private double value = DEFAULT_HEART_VALUE;

        /**
         * The Entity must be initialized.
         * @param e     Entity
         */
        public Builder(final Entity e) {
            this.e = e;
        }

        /**
         * 
         * @param enemyDamage   value of the damage to the enemies
         * @return              the blackHeart
         */
        public Builder enemyDamage(final double enemyDamage) {
            this.damage = enemyDamage;
            return this;
        }

        /**
         * 
         * @param heartValue    value of the heart
         * @return              the blackHeart
         */
        public Builder heartValue(final double heartValue) {
            this.value = heartValue;
            return this;
        }

        /**
         * @return      actual blackHeart
         */
        public BlackHeart build() {
            Objects.requireNonNull(this.e);
            return new BlackHeart(this.e, this.damage, this.value);
        }
    }

    /**
     * {@link inherit Doc}.
     */
    @Override
    public double getDamaged(final double damageValue) {
        if (damageValue < super.getValue()) {
           super.setValue(super.getValue() - damageValue);
            return 0;
        } else {
            final double tempValue = super.getValue();
            this.myEntity.getRoom().getEntities().stream()
                .filter(i -> i.hasComponent(AbstractMentalityComponent.class))
                .filter(i -> i.getComponent(EnemyMentalityComponent.class).isPresent())
                .forEach(i -> i.postEvent(new DamageEvent(this.myEntity, enemyDamage)));
            super.setValue(0);
            return damageValue - tempValue;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HeartEnum getColor() {
        return BasicHeartEnum.BLACK;
    }
}
