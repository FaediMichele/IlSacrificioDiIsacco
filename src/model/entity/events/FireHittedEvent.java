package model.entity.events;

import model.entity.Entity;

/**
 * Events when the fire has been damaged by the player.
 */
public class FireHittedEvent extends AbstractEvent {

    /**
     * 
     * @param sourceEntity the source entity
     */
    public FireHittedEvent(final Entity sourceEntity) {
        super(sourceEntity);
    }

}
