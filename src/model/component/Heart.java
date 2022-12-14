package model.component;

import java.util.Optional;

import model.enumeration.HeartEnum;

/**
 * 
 * The heart represents the life of the entity.
 *
 */

public interface Heart {
    /**
     * 
     * @return the max value an heart can have
     */
    double getMaxValue();

    /**
     * The way the heart is damaged could change in different kind of hearts.
     * 
     * @param damageValue total damageValue of the Health
     * @return 0 if this heart consumed all the damageValue, a double greater than 0
     *         if there is still some damageValue to process by other hearts
     */
    double getDamaged(double damageValue);

    /**
     * Method to call when this heart dies.
     */
    void died();

    /**
     * 
     * @return actual value of the heart.
     */
    double getValue();

    /**
     * adds a value to the actual one.
     * @param addValue the value
     * @return the value that still needs to be added.
     */
    double addValue(double addValue);

    /**
     * @return the maximum number of hearts of this kind that can be collected
     */
    Optional<Double> getMaxHearts();

    /**
     * . 
     * @return colour for heart.
     */
    HeartEnum getColour();
}
