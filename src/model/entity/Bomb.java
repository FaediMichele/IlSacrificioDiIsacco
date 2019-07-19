package model.entity;

import model.component.BodyComponent;
import model.component.DamageComponent;
import model.component.StatusComponent;
import model.component.collectible.BombCollectableComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.PsychoMentalityComponent;

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
        this.attachComponent(new PsychoMentalityComponent(this));
        this.attachComponent(new BombCollectableComponent(this, 3, 1000, 100));
        this.attachComponent(new DamageComponent(this, 0.5));
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
