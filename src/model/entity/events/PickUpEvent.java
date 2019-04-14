package model.entity.events;

import model.entity.Entity;


/**
 * 
 * This class models the event to collect an object.
 *
 */
public class PickUpEvent extends AbstractEvent {

    /**
     * Initialize the sources.
     * 
     * @param sourceEntity is the entities to be collected
     */
    public PickUpEvent(final Entity sourceEntity) {
        super(sourceEntity);
    }
}
