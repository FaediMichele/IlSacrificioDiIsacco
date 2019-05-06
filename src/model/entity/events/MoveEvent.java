package model.entity.events;

import model.entity.Entity;

/**
 * Event that triggers the movement of the entity.
 */
public class MoveEvent extends AbstractEvent {

    private final double xMove;
    private final double yMove;
    private final double zMove;

    /**
    * 
    * @param sourceEntity the source entity
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

    /**
     * 
     * @return xMove is the component of movement along the x-axis
     */
    public double getxMove() {
        return this.xMove;
    }

    /**
     * 
     * @return yMove is the component of movement along the y-axis
     */
    public double getyMove() {
        return this.yMove;
    }

    /**
     * 
     * @return zMove is the component of movement along the z-axis
     */
    public double getzMove() {
        return this.zMove;
    }
}
