package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collectible.HeartPickupableComponent;
import model.component.collision.CollisionComponent;
import model.enumeration.BasicHeartEnum;
import model.enumeration.EntityEnum;

/**
 * Implements a generic heart.
 */
public class Heart extends AbstractStaticEntity {
    private static final double WIDTH = 0.5;
    private static final double HEIGHT = 0.5;
    private static final int WEIGHT = 1;
    private static final EntityEnum ENTITY_NAME = BasicHeartEnum.RED;

    /**
     * Create a heart based on his position.
     * @param x the x-axis.
     * @param y the y-axis.
     */
    public Heart(final double x, final double y) {
        super();
        build(x, y);
    }

    /**
     * Empty constructor.
     * Set the position to (0.0, 0.0)s
     */
    public Heart() {
        super();
        build(0, 0);
    }

    /**
     * Create a new heart with a String.
     * @param args string Contains a map like " X="10.0" Y="20.0" ".
     */
    public Heart(final String args) {
        super();
        final Map<String, String> holder = Splitter.on(",").trimResults()
                .withKeyValueSeparator("=").split(args);
        build(Double.parseDouble(holder.get("X").replace("\"", "")),
                Double.parseDouble(holder.get("Y").replace("\"", "")));
    }

    private void build(final double x, final double y) {
        this.attachComponent(new HeartPickupableComponent(this));
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
