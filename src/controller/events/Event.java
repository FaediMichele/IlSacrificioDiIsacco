package controller.events;

import controller.Controller;

/**
 * Base interface for all the events.
 */
public interface Event {
    /**
     * Returns the {@link Entity} who triggered the event.
     * 
     * @return the entity
     */
    Controller getSourceController();
}
