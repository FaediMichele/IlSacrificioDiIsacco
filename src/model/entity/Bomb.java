package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collectible.BombCollectableComponent;
import model.component.collision.CollisionComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

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
        build(x, y);
    }

    /**
     * Default empty constructor.
     */
    public Bomb() {
        super();
        build(WIDTH, HEIGHT);
    }

    /**
     * Create a new bomb with a String.
     * @param args string Contains a map like " X="10.0" Y="20.0" ".
     */
    public Bomb(final String args) {
        super();
        Map<String, String> holder = Splitter.on(",").trimResults()
                .withKeyValueSeparator("=").split(args);
        build(Double.parseDouble(holder.get("X").replace("\"", "")),
                Double.parseDouble(holder.get("Y").replace("\"", "")));
    }

    private void build(final double x, final double y) {
        this.attachComponent(new BombCollectableComponent(this, 3, 1000, 100));
        this.setDefaultComponents(new BodyComponent(this, x, y, 0, HEIGHT, WIDTH, WEIGHT),
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
