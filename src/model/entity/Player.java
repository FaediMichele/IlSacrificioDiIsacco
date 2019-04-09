package model.entity;

import model.component.BodyComponent;
import model.component.Component;
import model.component.HealthComponent;

/**
 * Implements Player.
 */
public class Player extends AbstractMovableEntity {

    /**
     * Basic constructor.
     */
    public Player() {
        this(new BodyComponent(), null);
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Player(final BodyComponent entityBody, final Component entityCollision) {
        super(entityBody, entityCollision);
        attachComponent(new HealthComponent());
    }
}
