package model.entity;

import model.component.collision.LockCollisionComponent;

/**
 * 
 * Simple entity closed by a padlock.
 *
 */
public class SimpleLockEntity extends AbstractStaticEntity {

    SimpleLockEntity() {
        super();
        this.attachComponent(new LockCollisionComponent(this));
    }
}
