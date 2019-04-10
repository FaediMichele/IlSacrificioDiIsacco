package model.entity.events;

import com.google.common.eventbus.Subscribe;

import util.Lambda;

/**
 * .
 */
public class CollisionListener extends AbstractEventListener<CollisionEvent> {
    /**
     * 
     * @param l a
     */
    public CollisionListener(final Lambda<CollisionEvent> l) {
        super(l);
    }

    /**
     * 
     */
    @Override
    @Subscribe
    public void listenEvent(final CollisionEvent event) {
        super.listenEvent(event);
    }

}
