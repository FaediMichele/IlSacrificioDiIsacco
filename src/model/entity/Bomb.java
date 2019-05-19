package model.entity;

import model.component.BodyComponent;
import model.component.BombCollectibleComponent;
import model.component.CollisionComponent;
import model.component.DamageComponent;
import model.component.Mentality;
import model.component.MentalityComponent;

/**
 * 
 * Entity of the bomb positioned and primed.
 *
 */
public class Bomb extends AbstractEntity {

    /**
     * Default constructor.
     */
    public Bomb() {
        super();
        this.attachComponent(new MentalityComponent(this, Mentality.NEUTRAL));
        this.attachComponent(new BombCollectibleComponent(this, 3, 1000, 100));
        this.attachComponent(new DamageComponent(this, 0.5));
    }

    /**
     * @param entityBody Body of the entity
     * @param entityCollision Collision component of the entity
     */
    public Bomb(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        this.setDefaultComponents(entityBody, entityCollision);
    }

}
