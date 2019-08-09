package model.entity;

import model.enumeration.BasicHeartEnum;

/**
 * Implements a black heart.
 */
public class BlackPickupableHeart extends AbstractPickupableHeart {

    /**
     * Create a heart based on his position.
     * @param x the x-axis.
     * @param y the y-axis.
     */
    public BlackPickupableHeart(final double x, final double y) {
        super(x, y, BasicHeartEnum.BLACK);
    }

    /**
     * Empty constructor.
     * Set the position to (0.0, 0.0)s
     */
    public BlackPickupableHeart() {
        super(BasicHeartEnum.BLACK);
    }

    /**
     * Create a new heart with a String.
     * @param args string Contains a map like " X="10.0" Y="20.0" ".
     */
    public BlackPickupableHeart(final String args) {
        super(args, BasicHeartEnum.BLACK);
    }
}
