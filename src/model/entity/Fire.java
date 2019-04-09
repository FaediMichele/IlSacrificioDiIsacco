package model.entity;

import model.component.BodyComponent;
import model.component.Component;

/**
 * Implements the fires.
 */
public class Fire extends AbstractStaticEntity {

    /**
     * Basic constructor.
     */
    public Fire() {
        this(new BodyComponent(), null);
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Fire(final BodyComponent entityBody, final Component entityCollision) {
        super(entityBody, entityCollision);
    }
}
