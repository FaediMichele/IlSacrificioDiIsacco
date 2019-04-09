package model.entity.events;

import com.google.common.eventbus.Subscribe;

/**
 * The base class for all the first kind of events.
 * 
 * @param <E> the kind of event
 */
public abstract class AbstractEventListener<E extends AbstractEvent> implements EventListener<E> {

    /**
     * Base builder.
     */
    public AbstractEventListener() {
        // TODO Auto-generated constructor stub
    }

    @Override
    @Subscribe
    public final void listenEvent(final E event) {
        // TODO
    }
}
