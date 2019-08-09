package model.component;

import model.enumeration.BasicHeartEnum;
import model.enumeration.HeartEnum;
import model.entity.Entity;
/**
 * 
 * The simplest kind of heart.
 *
 */
public class SimpleHeart extends AbstractHeart {

    /**
     * 
     * @param myEntity 
     * @param value 
     */
    public SimpleHeart(final Entity myEntity, final double value) {
        super(myEntity, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void died() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HeartEnum getColor() {
        return BasicHeartEnum.RED;
    }
}
