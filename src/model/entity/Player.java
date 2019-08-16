package model.entity;

import model.component.BodyComponent;
import model.component.HealthComponent;
import model.component.InputComponent;
import model.component.InventoryComponent;
import model.component.MoveComponent;
import model.component.StatusComponent;
import model.component.TearWeaponComponent;
import model.component.collision.CollisionComponent;
import model.component.collision.MovableCollisionComponent;
import model.component.collision.PlayerCollisionComponent;
import model.component.mentality.PlayerMentalityComponent;
import model.enumeration.BasicTearEnum;
import model.enumeration.EntityEnum;
import model.enumeration.PlayerEnum;
import model.util.DataPlayer;
import model.util.Position;
import model.util.StatisticsInformations;

/**
 * Implements Player.
 */
public class Player extends AbstractMovableEntity {
    private static final Double HEIGHT = 20.0;
    private static final Double WIDTH = 25.0;

    private final PlayerEnum name;
    /**
     * Empty constructor.
     */
//    public Player() {
//        super();
//        this.attachComponent(new HealthComponent(this))
//            .attachComponent(new InventoryComponent(this))
//            .attachComponent(new PlayerMentalityComponent(this))
//            .attachComponent(new TearWeaponComponent(this))
//            .attachComponent(new PlayerCollisionComponent(this))
//            .attachComponent(new BodyComponent(this))
//            .attachComponent(new StatusComponent(this));
//        this.detachComponent(CollisionComponent.class);
//        this.detachComponent(MovableCollisionComponent.class);
//        this.name = BasicPlayerEnum.ISAAC;
//        }

    /**
     * Empty constructor.
     * @param data .
     */
    public Player(final DataPlayer data) {
        super();
        this.attachComponent(new HealthComponent(this, data.getLife()))
            .attachComponent(new InventoryComponent(this))
            .attachComponent(new PlayerMentalityComponent(this))
            .attachComponent(new TearWeaponComponent(this, data.getDamage(), BasicTearEnum.NORMAL, data.getRate()))
            .attachComponent(new PlayerCollisionComponent(this))
            .attachComponent(new BodyComponent(this, new Position(100.0, 100.0, 0.0), WIDTH, HEIGHT, 5))
            .attachComponent(new StatusComponent(this))
            .attachComponent(new MoveComponent(this, data.getSpeed()))
            .attachComponent(new InputComponent(this));
        this.name = data.getName();
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
        return this.name;
    }

    /**
     * 
     * @return the {@link StatisticsInformations}
     */
    public StatisticsInformations getStatisticsInformations() {
        final StatisticsInformations statistics = new StatisticsInformations();
        statistics.setBombs(this.getComponent(InventoryComponent.class).get().thingsOfThisKind(Bomb.class));
        statistics.setKeys(this.getComponent(InventoryComponent.class).get().thingsOfThisKind(Key.class));
        statistics.setHearts(this.getComponent(HealthComponent.class).get().getHeartPairs());
        return statistics;
    }
}
