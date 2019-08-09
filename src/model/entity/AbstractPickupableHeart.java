package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collectible.HeartPickupableComponent;
import model.component.collision.CollisionComponent;
import model.enumeration.EntityEnum;
import model.enumeration.HeartEnum;

/**
 * Implements a generic heart.
 */
public class AbstractPickupableHeart extends AbstractStaticEntity {
    private static final double WIDTH = 0.5;
    private static final double HEIGHT = 0.5;
    private static final int WEIGHT = 1;
    private final HeartEnum entityName;

    AbstractPickupableHeart(final double x, final double y, final HeartEnum entityName) {
        super();
        this.entityName = entityName;
        build(x, y);
    }

    AbstractPickupableHeart(final HeartEnum entityName) {
        super();
        this.entityName = entityName;
        build(0, 0);
    }

    AbstractPickupableHeart(final String args, final HeartEnum entityName) {
        super();
        this.entityName = entityName;
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
        return entityName;
    }

}
