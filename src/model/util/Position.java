package model.util;

import util.Triplet;

/**
 * Tuple of Double.
 */
public class Position extends Triplet<Double, Double, Double> {

    /**
     * Create a new Triplet.
     * @param x the X
     * @param y the Y
     * @param z the Z
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

    /**
     * Get the length of the vector defined by the x, y, z.
     * @return the length of the vector.
     */
    public double getVectorValue() {
        return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
    }

    /**
     * Set the length of the vector without changing it's direction.
     * @param newLength the length of the vector.
     * @return the Position.
     */
    public Position clipToLength(final double newLength) {
        final double originalLength = getVectorValue();
        this.setX(this.getX() * newLength / originalLength);
        this.setY(this.getY() * newLength / originalLength);
        this.setZ(this.getZ() * newLength / originalLength);
        return this;
    }


    /**
     * Get a new position with previous data.
     * @return a new position.
     */
    public Position getClone() {
        return new Position(getX(), getY(), getZ());
    }
}
