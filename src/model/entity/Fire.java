package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;

/**
 * Implements the fires.
 */
public class Fire extends AbstractStaticEntity {

    /**
     * Basic constructor.
     */
    public Fire() {
        this(new BodyComponent(), new CollisionComponent());
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Fire(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        super(entityBody, entityCollision);
    }
}
