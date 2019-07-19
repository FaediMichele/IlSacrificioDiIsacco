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
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    /**
     * Default SimpleHeart constructor.
     */
    public SimpleHeart() {
        this(DEFAULT_VALUE);
    }

    /**
     * {@inheritDoc}
     * 
     * If we want to extend this class we must prevent life from taking negative
     * values.
     */
    @Override
    public double getDamaged(final double damageValue) {
        final double tempValue = this.value;
        if (damageValue < this.value) {
            this.value -= damageValue;
            return 0;
        } else {
            this.value = 0;
            return damageValue - tempValue;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return this.value;
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
