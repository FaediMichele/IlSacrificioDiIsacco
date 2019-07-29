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

}
