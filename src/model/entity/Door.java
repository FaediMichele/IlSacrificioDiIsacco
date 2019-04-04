package model.entity;

import model.component.BodyComponent;
import model.component.Component;

/**
 * Implements the doors.
 */
public class Door extends AbstractStaticEntity {

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Door(final BodyComponent entityBody, final Component entityCollision) {
        super(entityBody, entityCollision);
    }
}
