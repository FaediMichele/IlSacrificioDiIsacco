package util;

import model.events.Event;

/**
 * The interfaces for all the first kind of event listeners.
 * 
 * @param <E> the kind of event
 */
public interface EventListener<E> {
    /**
     * The method that runs when the {@link Event} is triggered.
     * 
     * @param event the event
     */
    void listenEvent(E event);
}
