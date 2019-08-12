package model.events;

import model.entity.Entity;
import model.util.Position;

/**
 * Event that triggers the movement of the entity.
 */
public class MoveEvent extends AbstractEvent {

    private final Position movement;

    /**
    * 
    * @param sourceEntity the source entity
    * @param movement the position to which it needs to move
    */
    public MoveEvent(final Entity sourceEntity, final Position movement) {
        super(sourceEntity);
        this.movement = movement;
    }

    /**
     * 
     * @return the movement to accomplish
     */
    public Position getMovement() {
        return this.movement;
    }
}
