package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;

/**
 * Base class for all the static entities such as rocks and doors. See also
 * {@link AbstractEntity}.
 */
public class AbstractStaticEntity extends AbstractEntity {

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param entityStatus    the {@link StatusComponent}
     */
    public AbstractStaticEntity(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus) {
        super();
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }
/**
 * simple constructor of AbstractStaicEntity.
 */
    public AbstractStaticEntity() {
        super();
    }
}
