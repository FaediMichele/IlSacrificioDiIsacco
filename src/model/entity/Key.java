package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collectible.KeyCollectableComponent;
import model.component.collision.CollisionComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;
import model.util.Position;

/**
 * Key entity that can be collected.
 */
public class Key extends AbstractPickupableEntity {
    private static final double WIDTH = 15;
    private static final double HEIGHT = 15;
    private static final int WEIGHT = 1;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.KEY;


    /**
     * Default constructor.
     * @param x the position.
     * @param y the position.
     */
    public Key(final int x, final int y) {
        super();
        build(x, y);
    }

    /**
     * Default constructor.
     */
    public Key() {
        super();
        build(0, 0);
    }
    /**
     * Create a new key with a String.
     * @param args string Contains a map like " X="10.0" Y="20.0" ".
     */
    public Key(final String args) {
        super();
        final Map<String, String> holder = Splitter.on(",").trimResults()
                .withKeyValueSeparator("=").split(args);
        build(Double.parseDouble(holder.get("X").replace("\"", "")),
                Double.parseDouble(holder.get("Y").replace("\"", "")));
    }

    private void build(final double x, final double y) {
        this.setDefaultComponents(new BodyComponent(this, new Position(x, y, 0.0), HEIGHT, WIDTH, WEIGHT),
                new CollisionComponent(this), new StatusComponent(this));
        this.attachComponent(new KeyCollectableComponent(this));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }
}
