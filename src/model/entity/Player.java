package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.HealthComponent;
import model.component.InventoryComponent;

/**
 * Implements Player.
 */
public class Player extends AbstractMovableEntity {

    /**
     * Empty constructor.
     */
    public Player() {
        super();
        attachComponent(new HealthComponent(this));
        attachComponent(new InventoryComponent(this));
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Player(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        setDefaultComponents(entityBody, entityCollision);
    }
}
