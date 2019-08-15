package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.DamageComponent;
import model.component.HealthComponent;
import model.component.MonstroAIComponent;
import model.component.MoveComponent;
import model.component.StatusComponent;
import model.component.mentality.AbstractMentalityComponent;
import model.component.mentality.FlyingMentalityComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;
import model.util.Position;

/**
 * Implements the fly enemy.
 */
public class MonstroBoss extends AbstractEnemyMovable {
    private static final double WIDTH = 70;
    private static final double HEIGHT = 70;
    private static final int WEIGHT = 5;
    private static final double DSPEED = 1.5;
    private static final double DAMAGE = 1.0;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.MONSTRO;

    /**
     * Create a fly enemy based on his position.
     * @param x the x-axis.
     * @param y the y.axis.
     */
    public MonstroBoss(final double x, final double y) {
        super();
        build(x, y);
    }

    /**
     * Create a gaper enemy based on his position.
     */
    public MonstroBoss() {
        super();
        build(0, 0);
    }

    /**
     * Create a new caper with a String.
     * @param args string Contains a map like " X="10.0", Y="20.0" ".
     */
    public MonstroBoss(final String args) {
        super();
        final Map<String, String> holder = Splitter.on(",").trimResults()
                .withKeyValueSeparator("=").split(args);
        build(Double.parseDouble(holder.get("X").replace("\"", "")), 
                Double.parseDouble(holder.get("Y").replace("\"", "")));
    }

    private void build(final double x, final double y) {
        this.getComponent(AbstractMentalityComponent.class).get()
            .mergeWith(new FlyingMentalityComponent(this));
        this.attachComponent(new BodyComponent(this, new Position(x, y, 0.0), HEIGHT, WIDTH, WEIGHT))
            .attachComponent(new MoveComponent(this, DSPEED))
            .attachComponent(new StatusComponent(this))
            .attachComponent(new MonstroAIComponent(this))
            .attachComponent(new HealthComponent(this, 3))
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
