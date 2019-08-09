package model.entity;
import model.enumeration.BasicColorEnum;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

/**
 * Implements a generic heart.
 */
public class SimplePickupableHeart extends AbstractPickupableHeart {
    SimplePickupableHeart(double x, double y) {
        super(x, y, BasicColorEnum.RED);
    }
}
