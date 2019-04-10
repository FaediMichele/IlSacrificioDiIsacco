package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;

/**
 * Base class for all the static entities such as rocks and doors. See also
 * {@link AbstractEntity}.
 */
public class AbstractStaticEntity extends AbstractEntity {

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public AbstractStaticEntity(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        setDefaultComponents(entityBody, entityCollision);
        // TODO Auto-generated constructor stub
    }

    /**
     * Default constructor for static entities.
     */
    public AbstractStaticEntity() {
        super();
    }
}
