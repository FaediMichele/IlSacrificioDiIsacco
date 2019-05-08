package model.component;

import util.StaticMethodsUtils;;

/**
 * This class models the abstract heart.
 *
 */
public abstract class AbstractHart implements Heart {

	/**
	 * not do override.
	 */
	@Override
	public int hashCode() {
		return StaticMethodsUtils.hashCode(this);
	}

	/***
	 * not do override.
	 */
	@Override
	public boolean equals(final Object obj) {
		return StaticMethodsUtils.equals(this, obj);
	}

}
