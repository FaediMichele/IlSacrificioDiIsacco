package model.entity;

import model.component.collision.LockCollisionComponent;
import util.enumeration.BasicEntityEnum;
import util.enumeration.EntityEnum;

/**
 * 
 * Simple entity closed by a padlock.
 *
 */
public class SimpleLockEntity extends AbstractStaticEntity {
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.SIMPLE_LOCK_ENTITY;

    SimpleLockEntity() {
        super();
        this.attachComponent(new LockCollisionComponent(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }
}
