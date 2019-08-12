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

public class MoveComponent extends AbstractComponent<MoveComponent> {

    /**
     * Value of the entities that does not need to move.
     */
    public static final double NOMOVE = 0.0;

    /**
     * Default value for speed: 1.
     */
    public static final double DEFAULT_SPEED = 1.0;

    private double deltaSpeed;
    private Position movement = new Position(NOMOVE, NOMOVE, NOMOVE);
    private double lastMovementAngle;
    /**
     * 
     * @param deltaSpeed is the actual speed
     * @param entity     {@link Entity} for this component
     */
    public MoveComponent(final Entity entity, final double deltaSpeed) {
        super(entity);
        this.deltaSpeed = deltaSpeed;

        this.registerListener(new EventListener<MoveEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final MoveEvent event) {
                move(event.getMovement());
            }
        });
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
    }

    /**
     * @param move the move that must me done
     */
    private void move(final Position move) {
        this.movement.add(move);
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
        return this.movement;
    }

    @Override
    public final void update(final Double deltaTime) {
        if (this.checkMove()) {
            final Double time = deltaTime / 100;
            this.movement.scale(this.deltaSpeed * time);
            this.getBody().changePosition(this.movement);
            this.postLogs();
            this.initMove();
            this.setLastMovementAngle();
        }
    }

    /**
     * Set all speed to 0.
     */
    public void initMove() {
        this.movement = new Position(NOMOVE, NOMOVE, NOMOVE);
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
