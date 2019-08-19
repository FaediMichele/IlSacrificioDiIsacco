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
    private double tearRate;

    /**
     * 
     * @return name of player.
     */
    public PlayerEnum getName() {
        return name;
    }

    /**
     * 
     * @param name of player.
     * @return this.
     */
    public DataPlayer setName(final PlayerEnum name) {
        this.name = name;
        return this;
    }

    /**
     * Set the rate of fire.
     * @param rate the rate.
     * @return the entity.
     */
    public DataPlayer setRate(final double rate) {
        tearRate = rate;
        return this;
    }

    /**
     * Get the rate of fire.
     * @return the rate of fire.
     */
    public double getRate() {
        return tearRate;
    }

    /**
     * 
     * @return life of player.
     */
    public double getLife() {
        return life;
    }

    /**
     * 
     * @param life of player.
     * @return this.
     */
    public DataPlayer setLife(final double life) {
        this.life = life;
        return this;
    }

    /**
     * 
     * @return speed of player.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * 
     * @param speed of player.
     * @return this.
     */
    public DataPlayer setSpeed(final double speed) {
        this.speed = speed;
        return this;
    }

    /**
     * 
     * @return damage of player.
     */
    public double getDamage() {
        return damage;
    }

    /**
     * 
     * @param damage of player.
     * @return this.
     */
    public DataPlayer setDamage(final double damage) {
        this.damage = damage;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return  "name     = " + this.name     + "\n" 
                + "life     = " + this.life     + "\n" 
                + "speed    = " + this.speed    + "\n" 
                + "damage   = " + this.damage   + "\n" 
                + "tearRate = " + this.tearRate + "\n";
    }
}
