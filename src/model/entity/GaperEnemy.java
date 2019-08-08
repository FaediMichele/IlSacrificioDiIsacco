package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.FollowAIComponent;
import model.component.MoveComponent;
import model.component.StatusComponent;
import model.component.collision.MovableCollisionComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

/**
 * Implements the gaper enemy.
 */
public class GaperEnemy extends AbstractEnemyMovable {
    private static final double WIDTH = 20;
    private static final double HEIGHT = 20;
    private static final int WEIGHT = 5;
    private static final double DSPEED = 7;
//    private static final double MAXSPEED = 7;
//    private static final double FRICTION = 7;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.GAPER;


    /**
     * Create a gaper enemy based on his position.
     * @param x the x-axis.
     * @param y the y.axis.
     */
    public GaperEnemy(final double x, final double y) {
        super();
        build(x, y);
    }

    /**
     * Create a gaper enemy based on his position.
     */
    public GaperEnemy() {
        super();
        build(0, 0);
    }

    /**
     * Create a new caper with a String.
     * @param args string Contains a map like " X="10.0", Y="20.0" ".
     */
    public GaperEnemy(final String args) {
        super();
        final Map<String, String> holder = Splitter.on(",").trimResults()
                .withKeyValueSeparator("=").split(args);
        build(Double.parseDouble(holder.get("X").replace("\"", "")), 
                Double.parseDouble(holder.get("Y").replace("\"", "")));
    }

    private void build(final double x, final double y) {
        this.attachComponent(new BodyComponent(this, x, y, 0, HEIGHT, WIDTH, WEIGHT))
            .attachComponent(new MoveComponent(this, DSPEED))
            .attachComponent(new MovableCollisionComponent(this))
            .attachComponent(new StatusComponent(this))
            .attachComponent(new FollowAIComponent(this));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }
}
