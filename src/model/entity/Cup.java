package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.WinOnCollisionComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.NeutralMentalityComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;
import model.util.Position;
/**
 * Cup entity. This entity when touched by the player make the player go to the next floor.
 */
public class Cup extends AbstractStaticEntity {
    private static final double WIDTH = 20;
    private static final double HEIGHT = 20;
    private static final int WEIGHT = 1;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.ROCK;

    /**
     * Initialize entity.
     */
    public Cup() {
        super();
        this.setDefaultComponents(new BodyComponent(this, new Position(0.0, 0.0, 0.0), HEIGHT, WIDTH, WEIGHT, true),
                new CollisionComponent(this), new StatusComponent(this));
        this.attachComponent(new NeutralMentalityComponent())
            .attachComponent(new WinOnCollisionComponent(this, Player.class));
    }
    /**
     * {@inheritDoc}.
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }
}
