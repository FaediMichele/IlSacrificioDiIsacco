package model.component;

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
     * 
     * @return actual value of the heart.
     */
    double getValue();
}