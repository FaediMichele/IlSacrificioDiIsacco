package model.entity;

import java.util.Map;
import com.google.common.base.Splitter;
import model.component.BodyComponent;
import model.component.ObstacleComponent;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.NeutralMentalityComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.BasicMovementEnum;
import model.enumeration.EntityEnum;
import model.util.Position;
import util.Pair;

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
     * Create a wall that stay on the corner of the room.
     * @param direction the position of the wall.
     * @param roomSize the room size.
     */
    public Wall(final BasicMovementEnum direction, final Pair<Double, Double> roomSize) {
        super();
        final Pair<Pair<Double, Double>, Pair<Double, Double>> pos = setPosition(direction, roomSize);
        build(pos.getX().getX(), pos.getX().getY(), pos.getY().getX(), pos.getY().getY());
    }


    private Pair<Pair<Double, Double>, Pair<Double, Double>> setPosition(final BasicMovementEnum direction, final Pair<Double, Double> size) {
        switch (direction) {
        case UP:
            return new Pair<Pair<Double, Double>, Pair<Double, Double>>(new Pair<Double, Double>(0.2, 0.0),
                    new Pair<Double, Double>(size.getX() - 0.4, 0.01));
        case RIGHT:
            return new Pair<Pair<Double, Double>, Pair<Double, Double>>(new Pair<Double, Double>(size.getX() - 0.2, 0.3),
                    new Pair<Double, Double>(0.1, size.getY() - 0.6));
        case DOWN:
            return new Pair<Pair<Double, Double>, Pair<Double, Double>>(new Pair<Double, Double>(0.4, size.getY() - 0.2),
                    new Pair<Double, Double>(size.getX() - 0.2, 0.1));
        case LEFT:
            return new Pair<Pair<Double, Double>, Pair<Double, Double>>(new Pair<Double, Double>(0.0, 0.4),
                    new Pair<Double, Double>(0.01, size.getY() - 0.2));
        default:
            throw new IllegalArgumentException();
        }
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
     * @param args string Contains a map like " X="10.0" Y="20.0" " or "X="10.0" Y="20.0" W="50.0" H="5.0" ".
     */
    public Wall(final String args) {
        super();
        final Map<String, String> holder = Splitter.on(",").trimResults()
                .withKeyValueSeparator("=").split(args);
        if (holder.containsKey("H") && holder.containsKey("W")) {
            build(Double.parseDouble(holder.get("X").replace("\"", "")),
                    Double.parseDouble(holder.get("Y").replace("\"", "")),
                    Double.parseDouble(holder.get("W").replace("\"", "")),
                    Double.parseDouble(holder.get("H").replace("\"", "")));
        } else {
        build(Double.parseDouble(holder.get("X").replace("\"", "")),
                Double.parseDouble(holder.get("Y").replace("\"", "")));
        }
    }

    private void build(final double x, final double y) {
        build(x, y, WIDTH, HEIGHT);
    }
    private void build(final double x, final double y, final double width, final double height) {
        this.setDefaultComponents(new BodyComponent(this, new Position(x, y, 0.0), height, width, WEIGHT), new CollisionComponent(this),
                new StatusComponent(this));
        this.attachComponent(new NeutralMentalityComponent(this))
            .attachComponent(new ObstacleComponent(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }

}
