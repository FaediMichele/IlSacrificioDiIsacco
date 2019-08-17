package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.StatusComponent;

import model.component.collectible.StickyBombsPickupableComponent;
import model.component.collision.CollisionComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;
import model.util.Position;

/**
 * Implements the host hat.
 */
public class MawOfTheVoid extends AbstractPickupableEntity {
    private static final double WIDTH = 20;
    private static final double HEIGHT = 20;
    private static final int WEIGHT = 1;
    private final EntityEnum entityName = BasicEntityEnum.MAW_OF_THE_VOID;

    /**
     * 
     * @param x 
     * @param y 
     */
    public MawOfTheVoid(final double x, final double y) {
        super();
        build(x, y);
    }

    /**
     * 
     */
    public MawOfTheVoid() {
        super();
        build(0, 0);
    }

    /**
     * 
     * @param args 
     */
    public MawOfTheVoid(final String args) {
        super();
        final Map<String, String> holder = Splitter.on(",").trimResults().withKeyValueSeparator("=").split(args);
        build(Double.parseDouble(holder.get("X").replace("\"", "")),
                Double.parseDouble(holder.get("Y").replace("\"", "")));
    }

    private void build(final double x, final double y) {
        this.setDefaultComponents(new BodyComponent(this, new Position(x, y, 0.0), HEIGHT, WIDTH, WEIGHT),
                new CollisionComponent(this), new StatusComponent(this));
        this.attachComponent(new StickyBombsPickupableComponent(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return entityName;
    }

}
