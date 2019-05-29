package model.events;

import model.entity.Entity;

/**
 * TODO comment.
 *
 */
public class CollisionEvent extends AbstractEvent {

    /**
     * Create a new event for collision.
     * 
     * @param sourceEntity the entity that cause this event
     */
    public CollisionEvent(final Entity sourceEntity) {
        super(sourceEntity);
    }

}
