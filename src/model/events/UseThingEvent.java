package model.events;

import model.entity.Entity;

/**
 * Event when an entity wants to release an object it has collected.
 */
public class UseThingEvent extends AbstractEvent {

    private final Class<? extends Entity> releasedEntityClass;
    /**
     * Initialize the event.
     * @param sourceEntity entity that releases the object
     * @param releasedEntityClass the class of entity that is released into the room
     */
    public UseThingEvent(final Entity sourceEntity, final Class<? extends Entity> releasedEntityClass) {
        super(sourceEntity);
        this.releasedEntityClass = releasedEntityClass;
    }

    /**
     * getter for released entity.
     * @return released entity
     */
    public Class<? extends Entity> getReleasedEntityClass() {
        return this.releasedEntityClass;
    }

}
