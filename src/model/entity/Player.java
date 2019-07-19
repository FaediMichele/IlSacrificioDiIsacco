package model.entity;

import model.component.BodyComponent;
import model.component.HealthComponent;
import model.component.InventoryComponent;
import model.component.StatusComponent;
import model.component.TearWeaponComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.PlayerMentalityComponent;

/**
 * Implements Player.
 */
public class Player extends AbstractMovableEntity {

    /**
     * Empty constructor.
     */
    public Player() {
        super();
        this.attachComponent(new HealthComponent(this));
        this.attachComponent(new InventoryComponent(this));
        this.attachComponent(new PlayerMentalityComponent(this));
        this.attachComponent(new TearWeaponComponent(this));
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param entityStatus    the {@link StatusComponent}
     */
    public Player(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus) {
        this();
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }
}
