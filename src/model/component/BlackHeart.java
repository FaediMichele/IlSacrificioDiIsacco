package model.component;

import model.entity.events.DamageEvent;

/**
 * When a black heart value reaches 0, it damages all the enemy in the room.
 * Black heart uses the builder pattern to create itself.
 */

public class BlackHeart extends SimpleHeart {

    private final HealthComponent h;
    private final double enemyDamage;

    /**
     * 
     * @param h                 HealthComponent is needed when you create a black heart;
     * @param enemyDamage       value of the damage to the enemies
     * @param heartValue        value of the heart
     */
    protected BlackHeart(final HealthComponent h, final double enemyDamage, final double heartValue) {
        super(heartValue);
        this.enemyDamage = enemyDamage;
        this.h = h;
    }

    /**
     * Builder for BlackHeart.
     */
    public static class Builder {
        private static final double DEFAULT_ENEMY_DAMAGE = 0.3;
        private static final double DEFAULT_HEART_VALUE = 1;
        private final HealthComponent h;
        private double enemyDamage = DEFAULT_ENEMY_DAMAGE;
        private double heartValue = DEFAULT_HEART_VALUE;

        /**
         * The HealthComponent must be initialized.
         * @param h     healthComponent
         */
        public Builder(final HealthComponent h) {
            this.h = h;
        }

        /**
         * 
         * @param enemyDamage   value of the damage to the enemies
         * @return              the blackHeart
         */
        public Builder enemyDamage(final double enemyDamage) {
            this.enemyDamage = enemyDamage;
            return this;
        }

        /**
         * 
         * @param heartValue    value of the heart
         * @return              the blackHeart
         */
        public Builder heartValue(final double heartValue) {
            this.heartValue = heartValue;
            return this;
        }

        /**
         * @return      actual blackHeart
         */
        public BlackHeart build() {
            if (this.h == null) {
                throw new IllegalStateException();
            }
            return new BlackHeart(this.h, this.enemyDamage, this.heartValue);
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
            super.setValue(0);
            h.getEntity().getRoom().getEntity().stream()
                .filter(i -> i.hasComponent(MentalityComponent.class))
                .filter(i -> ((MentalityComponent) i.getComponent(MentalityComponent.class).get()).getMentality().equals(Mentality.EVIL))
                .forEach(i -> i.postEvent(new DamageEvent(h.getEntity(), enemyDamage)));
            return damageValue - tempValue;
        }
    }
}
