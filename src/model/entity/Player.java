package model.entity;

import model.component.BodyComponent;
import model.component.Component;

/**
 * Implements Player.
 */
public class Player extends AbstractMovableEntity {

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Player(final BodyComponent entityBody, final Component entityCollision) {
        super(entityBody, entityCollision);
    }
}
