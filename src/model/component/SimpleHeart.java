package model.component;

/**
 * 
 * The simplest kind of heart.
 *
 */
public class SimpleHeart implements Heart {

    private static final double DEFAULT_VALUE = 1;
    private double value;

    /**
     * Simple heart constructor.
     * 
     * @param value total value of the heart
     */
    public SimpleHeart(final double value) {
        this.value = value;
    }

    /**
     * Default SimpleHeart constructor.
     */
    public SimpleHeart() {
        this.value = DEFAULT_VALUE;
    }

    /**
     * {@inheritDoc} 
     * 
     * If we want to extend this class we must prevent life from
     * taking negative values.
     */
    @Override
    public double getDamaged(final double damageValue) {
        if (damageValue < value) {
            value = value - damageValue;
            return 0;
        } else {
            value = 0;
            return damageValue - getValue();
        }
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public double getValue() {
        return value;
    }
}
