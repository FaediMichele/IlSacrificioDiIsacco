package model.component;

/**
 * 
 * The simplest kind of heart.
 *
 */
public class SimpleHeart extends AbstractHeart {

    private static final double DEFAULT_VALUE = 1;
    private static final double MAX_VALUE = 1;
    private double value;

    /**
     * Simple heart constructor.
     * 
     * @param value total value of the heart
     */
    public SimpleHeart(final double value) {
        super();
        this.value = value;
    }

    /**
     * Default SimpleHeart constructor.
     */
    public SimpleHeart() {
        super();
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
            final double tempValue = value;
            value = 0;
            return damageValue - tempValue;
        }
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * 
     * @param value new value of the heart
     */
    protected void setValue(final double value) {
        this.value = value;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public double getMaxValue() {
        return MAX_VALUE;
    }
}
