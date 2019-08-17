package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collectible.HeartPickupableComponent;
import model.component.collision.CollisionComponent;
import model.enumeration.EntityEnum;
import model.enumeration.HeartEnum;
import model.util.Position;

/**
 * Implements a generic heart.
 */
public class AbstractPickupableHeart extends AbstractPickupableEntity {
    private static final double WIDTH = 20;
    private static final double HEIGHT = 20;
    private static final int WEIGHT = 1;
    private final HeartEnum heartColour;

    AbstractPickupableHeart(final double x, final double y, final HeartEnum entityName) {
        super();
        this.heartColour = entityName;
        build(x, y);
    }

    AbstractPickupableHeart(final HeartEnum entityName) {
        super();
        this.heartColour = entityName;
        build(0, 0);
    }

    AbstractPickupableHeart(final String args, final HeartEnum entityName) {
        super();
        this.heartColour = entityName;
        final Map<String, String> holder = Splitter.on(",").trimResults()
                .withKeyValueSeparator("=").split(args);
        build(Double.parseDouble(holder.get("X").replace("\"", "")),
                Double.parseDouble(holder.get("Y").replace("\"", "")));
    }

    private void build(final double x, final double y) {
        this.setDefaultComponents(new BodyComponent(this, new Position(x, y, 0.0), HEIGHT, WIDTH, WEIGHT),
                new CollisionComponent(this), new StatusComponent(this));
        this.attachComponent(new HeartPickupableComponent(this, heartColour));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return heartColour;
    }

}
