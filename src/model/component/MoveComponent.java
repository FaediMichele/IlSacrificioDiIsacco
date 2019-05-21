package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.events.EventListener;
import model.events.MoveEvent;

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
    public static final double DEFAULT_FRICTION = 0.001;

    private static final double MINIMIZE_SPACE_DELTA = 0.0001;
    private double deltaSpeed;
    private double xMove;
    private double yMove;
    private double zMove;
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
        super(entity);
        this.deltaSpeed = DEFAULT_SPEED;
        this.maxSpeed = DEFAULT_MAX_SPEED;
        this.friction = DEFAULT_FRICTION;
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
     * @param x move made on the x axis
     * @param y move made on the y axis
     * @param z move made on the z axis
     */
    protected void move(final double x, final double y, final double z) {
        System.out.println(xMove + " " + yMove + " " + zMove);
        this.xMove = this.xMove + x;
        this.yMove = this.yMove + y;
        this.zMove = this.zMove + z;
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
        final double spaceEachMove = calculateSpace(deltaTime) * MINIMIZE_SPACE_DELTA;
        this.getBody().changePosition(xMove * spaceEachMove, yMove * spaceEachMove, zMove * spaceEachMove);
        this.initMove();
    }

    private BodyComponent getBody() {
        return ((BodyComponent) this.getEntity().getComponent(BodyComponent.class).get());
    }
}
