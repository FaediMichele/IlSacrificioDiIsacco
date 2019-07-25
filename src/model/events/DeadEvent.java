package model.events;

import model.entity.Entity;

/**
 * Event launched when an entity die.
 */
public class DeadEvent extends AbstractEvent {
    /**
     * Create an event for the dead of an entity.
     * @param sourceEntity the entity that died.
     */
    public DeadEvent(final Entity sourceEntity) {
        super(sourceEntity);
    }
}
