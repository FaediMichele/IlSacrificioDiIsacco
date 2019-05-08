package model.component;

import model.entity.events.DamageEvent;

/**
 * When a black heart value reaches 0, it damages all the enemy in the room.
 *
 */

public class BlackHeart extends SimpleHeart {

    //private static final double DEFAULT_ENEMY_DAMAGE = 0.3;
    private final HealthComponent h;
    private final double enemyDamage;

    /**
     * 
     * @param h                 HealthComponent is needed when you create a black heart;
     * @param enemyDamage       the value to damage of the enemies when the black heart dies
     */
    public BlackHeart(final HealthComponent h, final double enemyDamage) {
        super();
        this.enemyDamage = enemyDamage;
        this.h = h;
    }

    /**
     * 
     * @param h                 HealthComponent of the heart
     * @param enemyDamage       the value of damage to the enemies when the black heart dies
     * @param heartValue        the value of the heart
     */
    public BlackHeart(final HealthComponent h, final double enemyDamage, final double heartValue) {
        super(heartValue);
        this.enemyDamage = enemyDamage;
        this.h = h;
    }

    /*
     * Da fare i casi h + heartValue + enemyDamage; h + heartValue; h + enemyDamage; h solo con il factory o il builder
     * */

    /**
     * {@link inherit Doc}.
     */
    @Override
    public double getDamaged(final double damageValue) {
        if (damageValue < super.getValue()) {
           super.setValue(super.getValue() - damageValue);
            return 0;
        } else {
            double tempValue = super.getValue();
            super.setValue(0);
            h.getEntity().getRoom().getEntity().stream()
                .filter(i -> i.hasComponent(MentalityComponent.class))
                .filter(i -> ((MentalityComponent) i.getComponent(MentalityComponent.class).get()).getMentality().equals(Mentality.EVIL))
                .forEach(i -> i.postEvent(new DamageEvent(h.getEntity(), enemyDamage)));
            return damageValue - tempValue;
        }
    }
}
