package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.DamageComponent;
import model.component.EnemyHealthComponent;
import model.component.FollowAIComponent;
import model.component.MoveComponent;
import model.component.StatusComponent;
import model.enumeration.BasicEnemyEnum;
import model.enumeration.EntityEnum;
import model.util.Position;

/**
 * Implements the gaper enemy.
 */
public class GaperEnemy extends AbstractEnemyMovable {
    private static final double WIDTH = 20;
    private static final double HEIGHT = 20;
    private static final int WEIGHT = 5;
    private static final double DSPEED = 1.2;
    private static final int HEALTH = 20;
    private static final double DAMAGE = 1;
    private static final EntityEnum ENTITY_NAME = BasicEnemyEnum.GAPER;


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
        this.attachComponent(new BodyComponent(this, new Position(x, y, 0.0), HEIGHT, WIDTH, WEIGHT, true))
            .attachComponent(new MoveComponent(this, DSPEED))
            .attachComponent(new StatusComponent(this))
            .attachComponent(new FollowAIComponent(this))
            .attachComponent(new EnemyHealthComponent(this, HEALTH))
            .attachComponent(new DamageComponent(this, DAMAGE));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }
}
