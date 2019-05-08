package model.component;

import util.StaticMethodsUtils;

/**
 * This class models the abstract heart.
 *
 */
public abstract class AbstractHart implements Heart {

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
