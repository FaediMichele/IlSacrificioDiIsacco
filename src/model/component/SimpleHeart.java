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
     * @param myEntity the entity to which it is attached
     * @param value actual value of the heart
     */
    public SimpleHeart(final Entity myEntity, final double value) {
        super(myEntity, value);
    }

    /**
     * 
     * @param myEntity the entity to which it is attached
     */
    public SimpleHeart(final Entity myEntity) {
        super(myEntity);
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
