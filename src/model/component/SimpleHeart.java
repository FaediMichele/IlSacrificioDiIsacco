package model.component;

/**
 * 
 * The simplest kind of heart.
 *
 */
public class SimpleHeart implements Heart {

    private static final double DEFAULT_VALUE = 1;
    private static final double DEFAULT_MAX_VALUE = 1;
    private static final double DEFAULT_MAX_HEARTS_OF_THIS_KIND = 3;
    private static final double DEFAULT_NUMBER_OF_HEARTS = 1;
    private double maxHeartsOfThisKind;
    private double numberOfHearts;
    private double lastHeartValue;
    private double maxValue;

    /**
     * Simple heart constructor.
     * 
     * @param lastHeartvalue value of the lastHeart, the only one that could me less than full.
     * @param numberOfHearts the actual number of hearts of this kind
     * @param maxHeartsOfThisKind the max number of the hearts
     * @param maxValue max value that the lastHeart can reach.
     */
    public SimpleHeart(final double lastHeartvalue, final double numberOfHearts, final double maxHeartsOfThisKind, final double maxValue) {
        this.maxValue = maxValue;
        this.numberOfHearts = numberOfHearts;
        this.maxHeartsOfThisKind = maxHeartsOfThisKind;
        this.lastHeartValue = lastHeartvalue;
    }

    /**
     * Default SimpleHeart constructor.
     */
    public SimpleHeart() {
        this.maxValue = DEFAULT_MAX_VALUE;
        this.numberOfHearts = DEFAULT_NUMBER_OF_HEARTS;
        this.lastHeartValue = DEFAULT_VALUE;
        this.maxHeartsOfThisKind = DEFAULT_MAX_HEARTS_OF_THIS_KIND;
    }

    /**
     * {@inheritDoc} 
     * 
     * If we want to extend this class we must prevent life from
     * taking negative values.
     */
    @Override
    public double getDamaged(final double damageValue) {
        double actualDamageValue = damageValue;
        while (actualDamageValue > 0) {
            if (actualDamageValue < this.lastHeartValue) {
                this.lastHeartValue = this.lastHeartValue - actualDamageValue;
                return 0;
            } 
            actualDamageValue = actualDamageValue - this.lastHeartValue;
            this.numberOfHearts--;
            if (this.numberOfHearts == 0) {
                return actualDamageValue;
            }
        }
        return 0;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public double getlastHeartValue() {
        return this.lastHeartValue;
    }

    /**
    * {@inheritDoc} 
    */
    @Override
    public double getMaxHeartsOfThisKind() {
        return this.maxHeartsOfThisKind;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public double getNumberOfHearts() {
        return this.numberOfHearts;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public void addHeart(final Heart newHeart) {
        if (this.numberOfHearts < this.maxHeartsOfThisKind 
                && newHeart.getNumberOfHearts() <= this.maxHeartsOfThisKind - this.numberOfHearts) {
            this.numberOfHearts = this.numberOfHearts + newHeart.getNumberOfHearts();
        } else {
            numberOfHearts = this.maxHeartsOfThisKind;
        }

        if (this.lastHeartValue + newHeart.getlastHeartValue() >= this.maxValue) {
            this.lastHeartValue = this.maxValue;
        } else {
            this.lastHeartValue = this.lastHeartValue + newHeart.getlastHeartValue();
        }
    }
}
