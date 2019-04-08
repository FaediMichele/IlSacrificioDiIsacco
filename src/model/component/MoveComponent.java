package model.component;

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
    private double maxSpeed;
    private final double friction;

    /**
      * 
      * @param deltaSpeed is the actual speed
      * @param maxSpeed is the max speed that can be reached
      * @param friction friction force against the movement
      */
    public MoveComponent(final double deltaSpeed, final double maxSpeed, final double friction) {
        super();
        this.deltaSpeed = deltaSpeed;
        this.maxSpeed = maxSpeed;
        this.friction = friction;
        init();
    }

    /**
     * Default MoveComponent constructor.
     */
    public MoveComponent() {
        super();
        this.deltaSpeed = DEFAULT_SPEED;
        this.maxSpeed = DEFAULT_MAX_SPEED;
        this.friction = DEFAULT_FRICTION;
        init();
    }
    /**
     * Getter for deltaSpeed.
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
     * @return the real speed space/ms, considering the friction force 
     */
    private double calculateSpace(final Double deltaTime) {
        double acceleration = -friction / this.getEntity().getBody().getWeight();
        double space = this.deltaSpeed * deltaTime + Math.pow(deltaTime, 2) * acceleration / 2;
        return space;
    }

    @Override
    public final void update(final Double deltaTime) {
        double spaceEachMove = calculateSpace(deltaTime);
        this.getEntity().getBody().changePosition(xMove * spaceEachMove, yMove * spaceEachMove, zMove * spaceEachMove);
        this.init();
    }

    @Override
    public final void init() {
        this.move(NOMOVE, NOMOVE, NOMOVE);
    }

}
