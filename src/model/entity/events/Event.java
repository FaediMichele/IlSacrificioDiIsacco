package model.entity.events;

import model.entity.Entity;

/**
 * Base interface for all the events.
 */
public interface Event {
    /**
     * Returns the {@link Entity} who triggered the event.
     * 
     * @return the entity
     */
    Entity getSourceEntity();
}
