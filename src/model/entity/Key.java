package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collectible.KeyCollectableComponent;
import model.component.collision.CollisionComponent;

/**
 * Key entity that can be collected.
 */
public class Key extends AbstractStaticEntity {
    /**
     * Default constructor.
     */
    public Key() {
        super();
        this.attachComponent(new KeyCollectableComponent(this));
    }

    /**
     * @param entityBody      Body of the entity
     * @param entityCollision Collision component of the entity
     * @param entityStatus    the {@link StatusComponent}
     */
    public Key(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus) {
        this();
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }
}
