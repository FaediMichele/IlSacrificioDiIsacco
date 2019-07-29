package model.events;

import model.entity.Entity;

/**
 * .
 *
 */
public class DoorChangeEvent extends AbstractEvent {

    /**
     * The {@link Event} for door changing.
     * 
     * @param sourceEntity the {@link Entity}
     */
    public DoorChangeEvent(final Entity sourceEntity) {
        super(sourceEntity);
    }

}
