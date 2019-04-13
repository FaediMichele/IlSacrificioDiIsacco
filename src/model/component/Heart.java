package model.component;

/**
 * 
 * The heart represents the life of the entity.
 *
 */

public interface Heart {

    /**
     * 
     * @return the value of the last heart
     */
    double getlastHeartValue();

    /**
     * The way the heart is damaged could change in different kind of hearts.
     * 
     * @param damageValue totals damageValue of the Health
     * @return 0 if this heart consumed all the damageValue, a double greater than 0
     *         if there is still some damageValue to process by other hearts
     */
    double getDamaged(double damageValue);

    /**
     * 
     * @return max number of hearts of this kind.
     */
    double getMaxHeartsOfThisKind();

    /**
     * 
     * @return numbers of hearts of this kind.
     */
    double getNumberOfHearts();

    /**
     * @param newHeart makes two heart of the same kind collapse
     */
     void addHeart(Heart newHeart);
}
