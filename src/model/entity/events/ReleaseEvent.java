package model.entity.events;

import model.entity.Entity;

/**
 * Event when an entity wants to release an object it has collected.
 */
public class ReleaseEvent extends AbstractEvent {

    private final Entity releasedEntity;
    /**
     * Initialize the event.
     * @param sourceEntity entity that releases the object
     * @param releasedEntity entity that is released into the room
     */
    public ReleaseEvent(final Entity sourceEntity, final Entity releasedEntity) {
        super(sourceEntity);
        this.releasedEntity = releasedEntity;
    }

    /**
     * getter for released entity.
     * @return released entity
     */
    public Entity getReleasedEntity() {
        return releasedEntity;
    }

}
