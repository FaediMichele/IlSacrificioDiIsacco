package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.NeutralMentalityComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

/**
 * Implements the walls.
 */
public class Wall extends AbstractStaticEntity {

    private static final double WIDTH = 1;
    private static final double HEIGHT = 1;
    private static final int WEIGHT = 1;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.WALL;


    /**
     * Default constructor.
     * 
     * @param x the position.
     * @param y the position.
     */
    public Wall(final int x, final int y) {
        super();
        build(x, y);
    }

    /**
     * Default constructor.
     * 
     */
    public Wall() {
        super();
        build(0, 0);
    }

    /**
     * Create a new wall with a String.
     * @param args string Contains a map like " X="10.0" Y="20.0" ".
     */
    public Wall(final String args) {
        super();
        final Map<String, String> holder = Splitter.on(" ").trimResults()
                .withKeyValueSeparator("=").split(args);
        build(Double.parseDouble(holder.get("X").replace("\"", "")),
                Double.parseDouble(holder.get("Y").replace("\"", "")));
    }

    private void build(final double x, final double y) {
        this.attachComponent(new NeutralMentalityComponent(this));
        this.setDefaultComponents(new BodyComponent(this, x, y, 0, HEIGHT, WIDTH, WEIGHT), new CollisionComponent(this),
                new StatusComponent(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }

}
