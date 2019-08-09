package model.entity;
import model.enumeration.BasicHeartEnum;

/**
 * Implements a generic heart.
 */
public class SimplePickupableHeart extends AbstractPickupableHeart {

    /**
     * Create a heart based on his position.
     * @param x the x-axis.
     * @param y the y-axis.
     */
    public SimplePickupableHeart(final double x, final double y) {
        super(x, y, BasicHeartEnum.RED);
    }

    /**
     * Empty constructor.
     * Set the position to (0.0, 0.0)s
     */
    public SimplePickupableHeart() {
        super(BasicHeartEnum.RED);
    }

    /**
     * Create a new heart with a String.
     * @param args string Contains a map like " X="10.0" Y="20.0" ".
     */
    public SimplePickupableHeart(final String args) {
        super(args, BasicHeartEnum.RED);
    }
}
