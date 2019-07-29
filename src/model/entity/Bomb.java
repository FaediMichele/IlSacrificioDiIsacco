package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collectible.BombCollectableComponent;
import model.component.collision.CollisionComponent;
import util.enumeration.BasicEntityEnum;
import util.enumeration.EntityEnum;

/**
 * 
 * Entity of the bomb positioned and primed.
 *
 */
public class Bomb extends AbstractEntity {
    private static final double WIDTH = 0.5;
    private static final double HEIGHT = 0.5;
    private static final int WEIGHT = 1;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.BOMB;
    /**
     * Default constructor.
     * @param x the position.
     * @param y the position.
     */
    public Bomb(final double x, final double y) {
        super();
        this.attachComponent(new BombCollectableComponent(this, 3, 1000, 100))
            .attachComponent(new BodyComponent(this, x, y, 0, HEIGHT, WIDTH, WEIGHT));
        this.setDefaultComponents(new BodyComponent(this, x, y, 0, HEIGHT, WIDTH, WEIGHT),
                new CollisionComponent(this), new StatusComponent(this));
    }

    /**
     * Default empty constructor.
     */
    public Bomb() {
        super();
        this.attachComponent(new BombCollectableComponent(this, 3, 1000, 100))
            .attachComponent(new BodyComponent(this, HEIGHT, WIDTH, WEIGHT));
        this.setDefaultComponents(new BodyComponent(this, HEIGHT, WIDTH, WEIGHT),
                new CollisionComponent(this), new StatusComponent(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }

}
