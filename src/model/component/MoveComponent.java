package model.component;

import com.google.common.eventbus.Subscribe;
import model.entity.Entity;
import model.enumeration.BasicMovementEnum;
import model.events.MoveEvent;
import model.util.Position;
import util.EventListener;
import util.Triplet;

/**
 * Component that manages the movement of the entity and its speed.
 *
 */

public class MoveComponent extends AbstractComponent {
    private static final double NOMOVE = 0.0;
    private static final double DEFAULT_SPEED = 1.0;
    private static final double DEFAULT_FRICTION = 60;
    private static final double SPEEDAPROX = 0.1;
    private static final double MAXSPEEDMULTIPLIER = 10; 
    private final double friction;
    private double deltaSpeed;
    private double maxSpeed;
    private final Position movement = new Position(NOMOVE, NOMOVE, NOMOVE);
    private double lastMovementAngle;
    private boolean hasMoved;

    /**
     * Create a move component with default friction.
     * @param entity the entity
     * @param deltaSpeed the acceleration.
     */
    public MoveComponent(final Entity entity, final double deltaSpeed) {
        this(entity, deltaSpeed, DEFAULT_FRICTION);
    }
    /**
     * Create a move component with friction.
     * @param deltaSpeed is the actual speed
     * @param entity {@link Entity} for this component
     * @param friction the friction of the entity in the space.
     */
    public MoveComponent(final Entity entity, final double deltaSpeed, final double friction) {
        super(entity);
        hasMoved = false;
        this.deltaSpeed = deltaSpeed / 100;
        this.friction = friction;
        this.registerListener(new EventListener<MoveEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final MoveEvent event) {
                hasMoved = true;
                move(event.getMovement());
            }
        });
        maxSpeed = deltaSpeed * MAXSPEEDMULTIPLIER;
    }

    /**
     * Create a move component with friction and max speed.
     * @param deltaSpeed is the actual speed
     * @param entity {@link Entity} for this component
     * @param friction the friction of the entity in the space.
     * @param maxSpeed the max Speed for the entity.
     */
    public MoveComponent(final Entity entity, final double deltaSpeed, final double friction, final double maxSpeed) {
        super(entity);
        hasMoved = false;
        this.deltaSpeed = deltaSpeed;
        this.friction = friction;
        this.registerListener(new EventListener<MoveEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final MoveEvent event) {
                hasMoved = true;
                move(event.getMovement());
            }
        });
        this.maxSpeed = maxSpeed;
    }

    /**
     * Default MoveComponent constructor.
     * 
     * @param entity {@link Entity} for this component
     */
    public MoveComponent(final Entity entity) {
        this(entity, DEFAULT_SPEED);
    }

    /**
     * Stop the speed.
     */
    public void stop() {
        initMove();
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
    protected void changeSpeed(final double deltaSpeed) {
        this.deltaSpeed = deltaSpeed;
    }

    /**
     * Move the entity to a direction.
     * @param angle the angle of the direction.
     */
    public final void move(final double angle) {
        move(new Position(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)), 0.0));
        hasMoved = true;
    }

    /**
     * @param move the move that must me done
     */
    public void move(final Position move) {
        this.movement.add(move);
        hasMoved = true;
    }

    /**
     * Check if there has been a new movement.
     */
    private boolean checkMove() {
        return movement.getX() != 0 || movement.getY() != 0  || movement.getZ() != 0;
    }

    /**
     * Getter for xMove.
     * @return xMove
     */
    public Triplet<Double, Double, Double> getMovement() {
        return new Position(this.movement.getX(), this.movement.getY(), this.movement.getZ());
    }

    @Override
    public final void update(final Double deltaTime) {
        if (this.checkMove()) {
            if (!hasMoved) {
                this.movement.scale(deltaTime / friction);
            } else {
                this.movement.scale(this.deltaSpeed * deltaTime);
                hasMoved = false;
            }
            if (this.movement.getVectorValue() > maxSpeed) {
                this.movement.clipToLength(maxSpeed);
            }
            this.getBody().changePosition(this.movement);
            this.postLogs();
            this.setLastMovementAngle();
            if (Math.abs(this.movement.getX()) < SPEEDAPROX
                    && Math.abs(this.movement.getY()) < SPEEDAPROX
                    && Math.abs(this.movement.getZ()) < SPEEDAPROX) {
                initMove();
            }
        }
    }

    /**
     * Set all speed to 0.
     */
    public void initMove() {
        this.movement.scale(0.0);
        hasMoved = true;
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
        final double deltaX = this.getBody().getPosition().getX() - this.getBody().getPreviousPosition().getX();
        final double deltaY = this.getBody().getPosition().getY() - this.getBody().getPreviousPosition().getY();
        this.lastMovementAngle = Math.atan2(deltaY, deltaX) * 180.0 / Math.PI;
    }

    private void postLogs() {
        final double x = Math.abs(this.movement.getX());
        final double y = Math.abs(this.movement.getY());
        if (y >= x) {
            if (this.movement.getY() <= 0) {
                this.getEntity().getStatusComponent().setMove(BasicMovementEnum.DOWN);
            } else {
                this.getEntity().getStatusComponent().setMove(BasicMovementEnum.UP);
            }
        } else if (this.movement.getX() >= 0) {
            this.getEntity().getStatusComponent().setMove(BasicMovementEnum.RIGHT);
        } else {
            this.getEntity().getStatusComponent().setMove(BasicMovementEnum.LEFT);
        }
    }
}
