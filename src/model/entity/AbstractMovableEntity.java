package model.entity;

import model.component.MoveComponent;
import model.component.collision.MovableCollisionComponent;

/**
 * Base class for all the movable entities. See also {@link AbstractEntity}.
 */
public abstract class AbstractMovableEntity extends AbstractEntity {

    /**
     * Default constructor for movable entities.
     */
    public AbstractMovableEntity() {
        super();
        this.attachComponent(new MoveComponent(this))
            .attachComponent(new MovableCollisionComponent(this));
    }
}
