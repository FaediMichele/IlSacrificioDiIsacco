package model.entity;

import java.util.Map;

import com.google.common.base.Splitter;

import model.component.BodyComponent;
import model.component.DamageComponent;
import model.component.EnemyHealthComponent;
import model.component.MonstroAIComponent;
import model.component.MoveComponent;
import model.component.StatusComponent;
import model.component.TearWeaponComponent;
import model.component.mentality.EnemyMentalityComponent;
import model.enumeration.BasicEnemyEnum;
import model.enumeration.BasicTearEnum;
import model.enumeration.EntityEnum;
import model.util.Position;

/**
 * Implements the fly enemy.
 */
public class MonstroBoss extends AbstractEnemyMovable {
    private static final double WIDTH = 79;
    private static final double HEIGHT = 112;
    private static final int WEIGHT = 5;
    private static final double DSPEED = 0.5;
    private static final double DAMAGE = 1.0;
    private static final double TEAR_RATE = 1000.0;
    private static final double TEAR_LIFE_TIME = 10000.0;
    private static final EntityEnum ENTITY_NAME = BasicEnemyEnum.MONSTRO;

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
        this.attachComponent(new BodyComponent(this, new Position(x, y, 0.0), HEIGHT, WIDTH, WEIGHT, true))
            .attachComponent(new MoveComponent(this, DSPEED))
            .attachComponent(new StatusComponent(this))
            .attachComponent(new MonstroAIComponent(this))
            .attachComponent(new EnemyHealthComponent(this, 3))
            .attachComponent(new DamageComponent(this, DAMAGE))
            .attachComponent(new TearWeaponComponent(this, DAMAGE, BasicTearEnum.BLOOD, TEAR_RATE))
            .attachComponent(new EnemyMentalityComponent());
        this.getComponent(TearWeaponComponent.class).get().setLifeTime(TEAR_LIFE_TIME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }

}
