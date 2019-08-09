package model.component;

import model.entity.Entity;
import model.enumeration.BasicColorEnum;
import model.enumeration.ColorHeartEnum;

/**
 * 
 * The simplest kind of heart.
 *
 */
public class SimpleHeart extends AbstractHeart {

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
    public ColorHeartEnum getColor() {
        return BasicColorEnum.RED;
    }
}
