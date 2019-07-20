package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collectible.BombCollectibleComponent;
import model.component.collision.CollisionComponent;

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
        this.attachComponent(new BombCollectibleComponent(this, 3, 1000, 100))
            .attachComponent(new BodyComponent(this));
    }

    /**
     * @param entityBody      Body of the entity
     * @param entityCollision Collision component of the entity
     * @param entityStatus    the {@link StatusComponent}
     */
    public Bomb(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus) {
        this();
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }

}
