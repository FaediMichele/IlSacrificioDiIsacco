package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;

/**
 * 
 * Entity of the bomb positioned and primed.
 *
 */
public class Bomb extends AbstractEntity {

    /**
     * @param entityBody Body of the entity
     * @param entityCollision Collision component of the entity
     */
    public Bomb(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        super(entityBody, entityCollision);
    }

}
