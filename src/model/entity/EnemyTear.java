package model.entity;

import model.component.mentality.EnemyMentalityComponent;
import model.component.mentality.PlayerMentalityComponent;

/**
 * The entity for the enemy tears, that has a different mentality.
 */
public class EnemyTear extends Tear {

    /**
     * Default constructor that modifies the mentality of the tear from the basic one.
     * 
     * @param angle           the angle of the tear when it's shot
     * @param entityThatShootedMe shooter entity
     * @param damage the Tear damage
     */
    public EnemyTear(final int angle, final Entity entityThatShootedMe, final double damage) {
        super(angle, entityThatShootedMe, damage);
        this.detachComponent(this.getComponent(PlayerMentalityComponent.class).get());
        this.attachComponent(new EnemyMentalityComponent(this));
    }
}
