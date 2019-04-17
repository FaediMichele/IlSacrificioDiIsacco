package model.entity.events;

import model.entity.Entity;

/**
 * Event when an entity wants to release an object it has collected.
 */
public class ReleaseEvent extends AbstractEvent {

    /**
     * Initialize the event.
     * @param sourceEntity entity that releases the object
     */
    public ReleaseEvent(final Entity sourceEntity) {
        super(sourceEntity);
    }

}
