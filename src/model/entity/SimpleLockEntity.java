package model.entity;

import model.component.collision.LockCollisionComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

/**
 * 
 * Simple entity closed by a padlock.
 *
 */
public class SimpleLockEntity extends AbstractStaticEntity {
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.SIMPLE_LOCK_ENTITY;

    SimpleLockEntity() {
        super();
        this.attachComponent(new LockCollisionComponent(this, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }
}
