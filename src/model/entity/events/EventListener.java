package model.entity.events;

import com.google.common.eventbus.Subscribe;

import util.Lambda;

/**
 * The base class for all the first kind of events.
 * 
 * @param <E> the kind of event
 */
public class EventListener<E extends Event> {

    private final Lambda<E> l;
    /**
     * Base builder.
     * @param l the lambda function
     */
    public EventListener(final Lambda<E> l) {
        this.l = l;
    }

    /**
     * The method that runs when the {@link Event} is triggered.
     * @param event the event
     */
    @Subscribe
    public final void listenEvent(final E event) {
        l.action(event);
    }
}
