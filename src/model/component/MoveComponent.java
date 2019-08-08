package model.component;

import com.google.common.eventbus.Subscribe;
import model.entity.Entity;
import model.enumeration.BasicMovementEnum;
import model.events.MoveEvent;
import util.EventListener;

/**
 * Component that manages the movement of the entity and its speed.
 *
 */

public class MoveComponent extends AbstractComponent<MoveComponent> {

    /**
     * Value of the entities that does not need to move.
     */
    public static final int NOMOVE = 0;

    /**
     * Default value for speed: 1.
     */
    public static final double DEFAULT_SPEED = 1;

    /**
     * Default maximum value for speed: 10.
     */
    public static final double DEFAULT_MAX_SPEED = 10;

    /**
     * Default value for friction: 0.001.
     */
    public static final double DEFAULT_FRICTION = 0.0001;

    private static final double MINIMIZE_SPACE_DELTA = 0.0001;
    private double deltaSpeed;
    private double xMove;
    private double yMove;
    private double zMove;
    private double lastMovementAngle;
    private final double maxSpeed;
    private final double friction;

    /**
     * 
     * @param deltaSpeed is the actual speed
     * @param maxSpeed   is the max speed that can be reached
     * @param friction   friction force against the movement
     * @param entity     {@link Entity} for this component
     */
    public MoveComponent(final Entity entity, final double deltaSpeed, final double maxSpeed, final double friction) {
        super(entity);
        this.deltaSpeed = deltaSpeed;
        this.maxSpeed = maxSpeed;
        this.friction = friction;
        this.initMove();

        this.registerListener(new EventListener<MoveEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final MoveEvent event) {
                move(event.getxMove(), event.getyMove(), event.getzMove());
            }
        });
    }

    /**
     * Default MoveComponent constructor.
     * 
     * @param entity {@link Entity} for this component
     */
    public MoveComponent(final Entity entity) {
        this(entity, DEFAULT_SPEED, DEFAULT_MAX_SPEED, DEFAULT_FRICTION);
    }

    /**
     * Getter for deltaSpeed.
     * 
     * @return deltaSpeed
     */
    public double getSpeed() {
        return this.deltaSpeed;
    }

    /**
     * @return friction
     */
    public double getFriction() {
        return friction;
    }

    /**
     * 
     * @param deltaSpeed is the speed in space/ms
     */
    protected void changeSpeed(final double deltaSpeed) {
        if (deltaSpeed > this.maxSpeed) {
            throw new IllegalArgumentException();
        }
        this.deltaSpeed = deltaSpeed;
    }

    /**
     * Move the entity to a direction.
     * @param angle the angle of the direction.
     */
    public final void move(final double angle) {
        move(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)), 0);
    }
    /**
     * @param x move made on the x axis
     * @param y move made on the y axis
     * @param z move made on the z axis
     */
    private void move(final double x, final double y, final double z) {
        this.xMove = this.xMove + x;
        this.yMove = this.yMove + y;
        this.zMove = this.zMove + z;
        //System.out.println(this.getClass().getName() + " : " + this.xMove + " " + this.yMove + " " + this.zMove);
    }

    /**
     * Check if there has been a new movement.
     */
    private boolean checkMove() {
        return xMove != 0 || yMove != 0 || zMove != 0;
    }

    /**
     * Initialize the movement to 0 as the entity has just been created or has just been moved.
     */
    private void initMove() {
        this.xMove = NOMOVE;
        this.yMove = NOMOVE;
        this.zMove = NOMOVE;
    }
    /**
     * Getter for xMove.
     * @return xMove
     */
    public double getxMove() {
        return this.xMove;
    }

    /**
     * Getter for yMove.
     * @return yMove
     */
    public double getyMove() {
        return this.yMove;
    }

    /**
     * Getter for zMove.
     * @return zMove
     */
    public double getzMove() {
        return this.zMove;
    }

    /**
     * Getter for maxSpeed.
     * @return maxSped
     */
    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    /**
     * space = v0*t +1/2*a*t^2.
     * 
     * @return the real speed space/ms, considering the friction force
     */
    private double calculateSpace(final Double deltaTime) {
        final double acceleration = -this.friction / this.getBody().getWeight();
        return this.deltaSpeed * deltaTime + Math.pow(deltaTime, 2) * acceleration / 2;
    }

    @Override
    public final void update(final Double deltaTime) {
        if (this.checkMove()) {
            final double spaceEachMove = calculateSpace(deltaTime) * MINIMIZE_SPACE_DELTA;
            this.getBody().changePosition(xMove * spaceEachMove, yMove * spaceEachMove, zMove * spaceEachMove);
            this.postLogs();
            this.initMove();
            this.setLastMovementAngle();
        }
    }

    private BodyComponent getBody() {
        if (this.getEntity().getComponent(BodyComponent.class).isPresent()) {
            return (this.getEntity().getComponent(BodyComponent.class).get());
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * @return the angle of the last Movement accomplished by the entity.
     */
    public double getLastMovementAngle() {
        return lastMovementAngle;
    }

    private void setLastMovementAngle() {
        final double deltaX = this.getBody().getPosition().getX() - this.getBody().getPositionPrevious().getX();
        final double deltaY = this.getBody().getPosition().getY() - this.getBody().getPositionPrevious().getY();
        this.lastMovementAngle = Math.atan2(deltaY, deltaX) * 180.0 / Math.PI;
    }

    private void postLogs() {
//        if (xMove > 0) {
//            this.getEntity().getStatusComponent().setMove(BasicMovementEnum.UP);
//        } else if (xMove < 0) {
//            this.getEntity().getStatusComponent().setMove(BasicMovementEnum.DOWN);
//        } else if (yMove > 0) {
//            this.getEntity().getStatusComponent().setMove(BasicMovementEnum.RIGHT);
//        } else if (yMove < 0) {
//            this.getEntity().getStatusComponent().setMove(BasicMovementEnum.LEFT);
//        }
        final double x = Math.abs(this.xMove);
        final double y = Math.abs(this.yMove);
        if (y >= x) {
            if (this.yMove <= 0) {
                this.getEntity().getStatusComponent().setMove(BasicMovementEnum.DOWN);
            } else {
                this.getEntity().getStatusComponent().setMove(BasicMovementEnum.UP);
            }
        } else if (this.xMove >= 0) {
            this.getEntity().getStatusComponent().setMove(BasicMovementEnum.RIGHT);
        } else {
            this.getEntity().getStatusComponent().setMove(BasicMovementEnum.LEFT);
        }
    }
}
