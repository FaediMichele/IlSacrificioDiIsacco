package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collectible.KeyCollectableComponent;
import model.component.collision.CollisionComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

/**
 * Key entity that can be collected.
 */
public class Key extends AbstractStaticEntity {
    private static final double WIDTH = 0.5;
    private static final double HEIGHT = 0.5;
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
        Map<String, String> holder = Splitter.on(" ").trimResults()
                .withKeyValueSeparator("=").split(args);
        build(Double.parseDouble(holder.get("X")), Double.parseDouble(holder.get("Y")));
    }

    private void build(final double x, final double y) {
        this.attachComponent(new KeyCollectableComponent(this));
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
