package model.component;

import model.entity.Entity;
import model.enumeration.HeartEnum;
import util.StaticMethodsUtils;

/**
 * This class models the abstract heart.
 *
 */
public abstract class AbstractHeart implements Heart {

    private static final double MAX_VALUE = 1;
    private static final double DEFAULT_VALUE = 1;
    private double value;
    private final Entity myEntity;

    /**
     * Basic heart constructor.
     * 
     * @param value total value of the heart
     * @param myEntity 
     */
    AbstractHeart(final Entity myEntity, final double value) {
        super();
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException();
        }
        this.value = value;
        this.myEntity = myEntity;
    }

    AbstractHeart(final Entity myEntity) {
        this (myEntity, DEFAULT_VALUE);
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
            this.died();
            this.value = 0;
            return damageValue - tempValue;
        }
    }

    /**
     * Method to call when this heart dies.
     */
    public abstract void died();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract HeartEnum getColor();

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

    /**
     * @return the entity
     */
    public Entity getMyEntity() {
        return myEntity;
    }

    /**
     * this method will generate a hash code for this object.
     */
    @Override
    public int hashCode() {
        return StaticMethodsUtils.hashCode(this);
    }

    /***
     * This method returns true if the object passing it is equal to this object.
     */
    @Override
    public boolean equals(final Object obj) {
        return StaticMethodsUtils.equals(this, obj);
    }

}
