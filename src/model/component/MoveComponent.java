package model.component;

import model.entity.Entity;

/**
 * Component that manages the movement of the entity and its speed.
 *
 */

public class MoveComponent extends AbstractComponent {

    private static final int NOMOVE = 0;
    private static final double DEFAULT_SPEED = 1;
    private static final double DEFAULT_MAX_SPEED = 10;
    private static final double DEFAULT_FRICTION = 0.001;
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
        this.move(NOMOVE, NOMOVE, NOMOVE);
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
        this.move(NOMOVE, NOMOVE, NOMOVE);
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
     * 
     * @param deltaSpeed is the speed in space/ms
     */
    public void changeSpeed(final double deltaSpeed) {
        if (deltaSpeed > maxSpeed) {
            throw new IllegalArgumentException();
        }
        this.deltaSpeed = deltaSpeed;
    }

    /**
     * @param x move made on the x axis
     * @param y move made on the y axis
     * @param z move made on the z axis
     */
    public void move(final int x, final int y, final int z) {
        this.xMove = this.xMove + x;
        this.yMove = this.yMove + y;
        this.zMove = this.zMove + z;
    }

    /**
     * space = v0*t +1/2*a*t^2.
     * 
     * @return the real speed space/ms, considering the friction force
     */
    private double calculateSpace(final Double deltaTime) {
        final double acceleration = -friction
                / ((BodyComponent) this.getEntity().getComponent(BodyComponent.class).get()).getWeight();
        return this.deltaSpeed * deltaTime + Math.pow(deltaTime, 2) * acceleration / 2;
    }

    @Override
    public final void update(final Double deltaTime) {
        final double spaceEachMove = calculateSpace(deltaTime);
        getBody().changePosition(xMove * spaceEachMove, yMove * spaceEachMove, zMove * spaceEachMove);
        this.move(NOMOVE, NOMOVE, NOMOVE);
    }

    private BodyComponent getBody() {
        return ((BodyComponent) this.getEntity().getComponent(BodyComponent.class).get());
    }

}
