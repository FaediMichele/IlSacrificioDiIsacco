package model.entity;

import model.component.BodyComponent;
import model.component.HealthComponent;
import model.component.InventoryComponent;
import model.component.StatusComponent;
import model.component.TearWeaponComponent;
import model.component.collision.CollisionComponent;
import model.component.collision.MovableCollisionComponent;
import model.component.collision.PlayerCollisionComponent;
import model.component.mentality.PlayerMentalityComponent;
import util.enumeration.BasicPlayerEnum;
import util.enumeration.EntityEnum;

/**
 * Implements Player.
 */
public class Player extends AbstractMovableEntity {
    /**
     * Empty constructor.
     */
    public Player() {
        super();
        this.attachComponent(new HealthComponent(this))
            .attachComponent(new InventoryComponent(this))
            .attachComponent(new PlayerMentalityComponent(this))
            .attachComponent(new TearWeaponComponent(this))
            .attachComponent(new PlayerCollisionComponent(this))
            .attachComponent(new BodyComponent(this))
            .attachComponent(new StatusComponent(this));
        this.detachComponent(CollisionComponent.class);
        this.detachComponent(MovableCollisionComponent.class);
        }

//    /**
//     * @param entityBody      the {@link BodyComponent}
//     * @param entityCollision the {@link CollisionComponent}
//     * @param entityStatus    the {@link StatusComponent}
//     */
//    public Player(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus) {
//        this();
//        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public  EntityEnum getNameEntity() {
        return BasicPlayerEnum.PLAYER;
    }
}
