package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.MoveComponent;

/**
 * Base class for all the movable entities. See also {@link AbstractEntity}.
 */
public abstract class AbstractMovableEntity extends AbstractEntity {

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public AbstractMovableEntity(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        this.setDefaultComponents(entityBody, entityCollision);
    }

    /**
     * Default constructor for movable entities.
     */
    public AbstractMovableEntity() {
        super();
        this.attachComponent(new MoveComponent(this));
    }
}
