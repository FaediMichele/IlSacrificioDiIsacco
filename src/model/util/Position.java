package model.util;

import util.Triplet;

/**
 * 
 * Is positions for entity.
 *
 */
public class Position extends Triplet<Double, Double, Double> {

    /**
     * 
     * @param x 
     * @param y 
     * @param z 
     */
    public Position(final Double x, final Double y, final  Double z) {
        super(x, y, z);
    }

    /**
     * Adds another triplet to this one.
     * @param toAdd the triplet to add
     */
    public void add(final Position toAdd) {
        this.setX(this.getX() + toAdd.getX());
        this.setY(this.getY() + toAdd.getY());
        this.setZ(this.getZ() + toAdd.getZ());
    }

    /**
     * Multiplies all the values for the scale value.
     * @param scale the scale value to use
     */
    public void scale(final double scale) {
        this.setX(this.getX() * scale);
        this.setY(this.getY() * scale);
        this.setZ(this.getZ() * scale);
    }
}
