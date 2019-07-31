package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collectible.HeartPickupableComponent;
import model.component.collision.CollisionComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

/**
 * Implements a generic heart.
 */
public class Heart extends AbstractStaticEntity {
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.HEART_RED;

    /**
     * 
     * @param entityBody the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param entityStatus the {@link StatusComponent}
     */
    public Heart(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus) {
        this();
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }

    /**
     * Main constructor.
     */
    public Heart() {
        super();
        this.attachComponent(new HeartPickupableComponent(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }

}
