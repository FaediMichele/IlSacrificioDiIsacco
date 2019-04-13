package model.component;

import model.entity.Entity;

/**
 * 
 * It is the component that generates tears when Isacco attacks.
 *
 */

public class SimpleTearWeapon extends AbstractComponent<SimpleTearWeapon> {

    SimpleTearWeapon(final Entity entity) {
        super(entity);
    }

    SimpleTearWeapon(final Entity entity, final SimpleTearWeapon component) {
        super(entity, component);
    }

}
