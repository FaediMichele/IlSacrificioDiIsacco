package model.entity.events;

import model.entity.Entity;

/**
 * Event that triggers the movement of the entity.
 */
@SuppressWarnings("unused")
public class MoveEvent extends AbstractEvent {

    private double xMove;
    private double yMove;
    private double zMove;

    /**
 * 
 * @param sourceEntity the entity source
 * @param xMove the move on the x axis
 * @param yMove the move on the y axis
 * @param zMove the move on the z axis
 */
    public MoveEvent(final Entity sourceEntity, final double xMove, final double yMove, final double zMove) {
        super(sourceEntity);
        this.xMove = xMove;
        this.yMove = yMove;
        this.zMove = zMove;
    }

}
