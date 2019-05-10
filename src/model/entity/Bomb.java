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
     * Default constructor.
     */
    public Bomb() {
        super();
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
