package model.events;

import model.entity.Entity;

/**
 * This events triggers the shot of a tear.
 */

public class TearShotEvent extends AbstractEvent {

    private final int angle;

    /**
     * @param sourceEntity entity that shots the tear
     * @param angle of the tear direction when it's shot
     */
    public TearShotEvent(final Entity sourceEntity, final int angle) {
        super(sourceEntity);
        this.angle = angle;
    }

    /**
     * @return direction
     */
    public int getAngle() {
        return this.angle;
    }

}
