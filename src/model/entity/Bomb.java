package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.MoveComponent;
import model.component.StatusComponent;
import model.component.collectible.BombCollectableComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.NeutralMentalityComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;
import model.util.Position;

/**
 * 
 * Entity of the bomb positioned and primed.
 *
 */
public class Bomb extends AbstractEntity {
    private static final double WIDTH = 20.0;
    private static final double HEIGHT = 20.0;
    private static final int WEIGHT = 1;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.BOMB;

    /**
     * Default empty constructor.
     */
    public Bomb() {
        super();
        this.attachComponent(new BombCollectableComponent(this))
                .attachComponent(new MoveComponent(this))
                .attachComponent(new BodyComponent(this, HEIGHT, WIDTH, WEIGHT))
                .attachComponent(new NeutralMentalityComponent(this));

    }

    /**
     * Create a new bomb with a String.
     * 
     * @param args string Contains a map like " X="10.0" Y="20.0" ".
     */
    public Bomb(final String args) {
        super();
        final Map<String, String> holder = Splitter.on(",").trimResults().withKeyValueSeparator("=").split(args);
        build(Double.parseDouble(holder.get("X").replace("\"", "")),
                Double.parseDouble(holder.get("Y").replace("\"", "")));
    }

    private void build(final double x, final double y) {
        this.setDefaultComponents(new BodyComponent(this, new Position(x, y, 0.0), HEIGHT, WIDTH, WEIGHT), new CollisionComponent(this),
                new StatusComponent(this));
        this.attachComponent(new BombCollectableComponent(this)).attachComponent(new NeutralMentalityComponent(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }

}
