package model.entity;

import model.component.BodyComponent;
import model.component.MoveComponent;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;

/**
 * Base class for all the movable entities. See also {@link AbstractEntity}.
 */
public abstract class AbstractMovableEntity extends AbstractEntity {

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param entityStatus    the {@link StatusComponent}
     */
    public AbstractMovableEntity(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus) {
        this();
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }

    /**
     * Default constructor for movable entities.
     */
    public AbstractMovableEntity() {
        super();
        this.attachComponent(new MoveComponent(this));
    }
}
