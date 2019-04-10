package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;

/**
 * Base class for all the static entities such as rocks and doors. See also
 * {@link AbstractEntity}.
 */
public class AbstractStaticEntity extends AbstractEntity {

    /**
     * Basic constructor.
     */
    public AbstractStaticEntity() {
        this(new BodyComponent(), new CollisionComponent());
    }

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public AbstractStaticEntity(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        super(entityBody, entityCollision);
        // TODO Auto-generated constructor stub
    }

}
