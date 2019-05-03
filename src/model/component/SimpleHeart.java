package model.component;

/**
 * 
 * The simplest kind of heart.
 *
 */
public class SimpleHeart implements Heart {

    private static final double DEFAULT_VALUE = 1;
    private static final double MAX_VALUE = 1;
    private static final double MAX_HEARTS_OF_THIS_KIND = 5;
    private static final double DEFAULT_NUMBER_OF_HEARTS = 3;
    private double numberOfHearts;
    private double lastHeartValue;

    /**
     * Simple heart constructor.
     * 
     * @param lastHeartvalue value of the lastHeart, the only one that could me less than full.
     * @param numberOfHearts the actual number of hearts of this kind
    */
    public SimpleHeart(final double lastHeartvalue, final double numberOfHearts) {
        this.numberOfHearts = numberOfHearts;
        this.lastHeartValue = lastHeartvalue;
    }

    /**
     * Default SimpleHeart constructor.
     */
    public SimpleHeart() {
        this.numberOfHearts = DEFAULT_NUMBER_OF_HEARTS;
        this.lastHeartValue = DEFAULT_VALUE;
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
            this.lastHeartValue = MAX_VALUE;
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
    public double getMaxValue() {
        return MAX_VALUE;
    }

    /**
    * {@inheritDoc} 
    */
    @Override
    public double getMaxHeartsOfThisKind() {
        return MAX_HEARTS_OF_THIS_KIND;
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
        if (this.numberOfHearts < MAX_HEARTS_OF_THIS_KIND
                && newHeart.getNumberOfHearts() <= MAX_HEARTS_OF_THIS_KIND - this.numberOfHearts) {
            this.numberOfHearts = this.numberOfHearts + newHeart.getNumberOfHearts();
        } else {
            numberOfHearts = MAX_HEARTS_OF_THIS_KIND;
        }

        if (this.lastHeartValue + newHeart.getlastHeartValue() <= MAX_HEARTS_OF_THIS_KIND) {
            this.lastHeartValue = this.lastHeartValue + newHeart.getlastHeartValue();
        } else {
            this.addHeart(new SimpleHeart(this.lastHeartValue + newHeart.getlastHeartValue() - MAX_VALUE, 1));
        }
    }
}
