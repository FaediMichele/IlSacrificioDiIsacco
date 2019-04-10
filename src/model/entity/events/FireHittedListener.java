package model.entity.events;

import com.google.common.eventbus.Subscribe;

import util.Lambda;

/**
 *
 *
 */
public class FireHittedListener extends AbstractEventListener<FireHittedEvent> {
    /**
     * Base builder.
     * 
     * @param l the lambda function
     */
    public FireHittedListener(final Lambda<FireHittedEvent> l) {
        super(l);
    }

    /**
     * The method that runs when the {@link Event} is triggered.
     * 
     * @param event the event
     */
    @Override
    @Subscribe
    public void listenEvent(final FireHittedEvent event) {
        super.listenEvent(event);
    }
}
