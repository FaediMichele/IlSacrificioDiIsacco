package model.util;

import model.enumeration.PlayerEnum;

/**
 * 
 * This class contains value used by PlayerFactory.
 *
 */
public class DataPlayer {

    private PlayerEnum name;
    private double life;
    private double speed;
    private double damage;

    /**
     * 
     * @return .
     */
    public PlayerEnum getName() {
        return name;
    }

    /**
     * 
     * @param name .
     * @return .
     */
    public DataPlayer setName(final PlayerEnum name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return .
     */
    public double getLife() {
        return life;
    }

    /**
     * 
     * @param life .
     * @return .
     */
    public DataPlayer setLife(final double life) {
        this.life = life;
        return this;
    }

    /**
     * 
     * @return .
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * 
     * @param speed .
     * @return .
     */
    public DataPlayer setSpeed(final double speed) {
        this.speed = speed;
        return this;
    }

    /**
     * 
     * @return .
     */
    public double getDamage() {
        return damage;
    }

    /**
     * 
     * @param damage .
     * @return .
     */
    public DataPlayer setDamage(final double damage) {
        this.damage = damage;
        return this;
    }
}
