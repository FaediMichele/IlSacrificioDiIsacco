package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.enumeration.EntityEnum;

/**
 * Base class for all the pickupable entities such as bombs and keys. See also
 * {@link AbstractEntity}.
 */
public abstract class AbstractPickupableEntity extends AbstractEntity {

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param entityStatus    the {@link StatusComponent}
     */
    public AbstractPickupableEntity(final BodyComponent entityBody, final CollisionComponent entityCollision,
            final StatusComponent entityStatus) {
        super();
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);

    }

    /**
     * simple constructor of AbstractPickupableEntity.
     */
    public AbstractPickupableEntity() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract EntityEnum getNameEntity();
}
