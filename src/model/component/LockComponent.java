package model.component;

import model.entity.Entity;

/**
 * lock of the some entities that needs to be opened, as doors.
 *
 */
public class LockComponent extends AbstractComponent<LockComponent> {

    private boolean locked = true;

    /**
     * 
     * @param entity my Entity
     */
    public LockComponent(final Entity entity) {
        super(entity);
    }

    /**
     * @return the state of the lock.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * sets the lock as opened.
     */
    public void unlock() {
        this.locked = true;
    }

}
