package model.component;

import model.entity.Entity;

/**
 * 
 * Component of damage that the entity inflicts on other enemy entities.
 *
 */
public class DamageComponent extends AbstractComponent<DamageComponent> {

    private double damage;

    DamageComponent(final Entity entity, final double damage) {
        super(entity);
        this.damage = damage;
    }
/**
 * 
 * @return the value of the damage that the entity inflicts at this time
 */
    protected double getDamege() {
        return this.damage;
    }

}
