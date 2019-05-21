package model.events;

import model.component.Directions;
import model.entity.Entity;

/**
 * This events triggers the shot of a tear.
 */

public class TearShotEvent extends AbstractEvent {

    private final Directions direction;

    /**
     * @param sourceEntity entity that shots the tear
     * @param direction the direction of the tear
     */
    public TearShotEvent(final Entity sourceEntity, final Directions direction) {
        super(sourceEntity);
        this.direction = direction;
    }

    /**
     * @return direction
     */
    public Directions getDirection() {
        return direction;
    }

}
