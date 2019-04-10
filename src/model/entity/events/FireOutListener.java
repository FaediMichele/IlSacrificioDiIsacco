package model.entity.events;

import com.google.common.eventbus.Subscribe;

import util.Lambda;

/**
 *
 *
 */
public class FireOutListener extends AbstractEventListener<FireOutEvent> {

    /**
     * Base builder.
     * 
     * @param l the lambda function
     */
    public FireOutListener(final Lambda<FireOutEvent> l) {
        super(l);
    }

    /**
     * The method that runs when the {@link Event} is triggered.
     * 
     * @param event the event
     */
    @Override
    @Subscribe
    public void listenEvent(final FireOutEvent event) {
        super.listenEvent(event);
    }
}
