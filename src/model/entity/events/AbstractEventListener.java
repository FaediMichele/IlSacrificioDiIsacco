package model.entity.events;

import util.Lambda;

/**
 * The base class for all the first kind of events.
 * 
 * @param <E> the kind of event
 */
public abstract class AbstractEventListener<E extends Event> implements EventListener<E> {

    private final Lambda<E> l;
    /**
     * Base builder.
     * @param l the lambda function
     */
    public AbstractEventListener(final Lambda<E> l) {
        this.l = l;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void listenEvent(final E event) {
        l.action(event);
    }
}
