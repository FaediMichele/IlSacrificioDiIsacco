package model.entity;

import model.component.BodyComponent;
import model.component.BombCollectibleComponent;
import model.component.CollisionComponent;
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
        attachComponent(new MentalityComponent(this, Mentality.NEUTRAL));
        attachComponent(new BombCollectibleComponent(this, 1, 1, 1));
    }

    /**
     * @param entityBody Body of the entity
     * @param entityCollision Collision component of the entity
     */
    public Bomb(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        setDefaultComponents(entityBody, entityCollision);
    }

}
