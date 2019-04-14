package model.entity.events;

import model.entity.Entity;

/**
 * Event when an entity wants to release an object it has collected.
 */
public class ReleaseBombEvent extends AbstractEvent {

    /**
     * Initialize the event.
     * @param sourceEntity entity that releases the object
     */
    public ReleaseBombEvent(final Entity sourceEntity) {
        super(sourceEntity);
    }

}
