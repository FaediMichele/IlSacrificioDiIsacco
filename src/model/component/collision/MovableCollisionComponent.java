package model.component.collision;

import java.util.List;

import model.component.BodyComponent;
import model.entity.Entity;
import model.events.CollisionEvent;
import model.events.MoveEvent;
import util.EventListener;

/**
 * 
 * This is the component of the collisions of all the entities that move.
 *
 */
public class MovableCollisionComponent extends CollisionComponent {
    /**
     * Default CollisionComponent constructor.
     * 
     * @param entity entity for this component
     */
    public MovableCollisionComponent(final Entity entity) {
        super(entity);
    }

    /**
     * Custom event Listener.
     * 
     * @param entity         the {@link Entity}
     * @param eventListeners the {@link EventListener}
     */
    public MovableCollisionComponent(final Entity entity, final List<EventListener<CollisionEvent>> eventListeners) {
        super(entity, eventListeners);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleCollision(final CollisionEvent event) {
        super.handleCollision(event);
        this.handleMovement(event);
    }

    /**
     * Handles the movement of the entities after collision.
     * 
     * @param event the {@link Event}
     */
    protected void handleMovement(final CollisionEvent event) {
        final double angle = 89;
        double a = Math.random() * angle, b = Math.random() * angle;
        while ((Math.cos(a) == 0 || Math.sin(b) - Math.cos(b) * Math.sin(a) == 0) && a + b > 90) {
            a = Math.random() * angle;
            b = Math.random() * angle;
        }

        final double v1xi = this.getBodyComponent(this.getEntity()).getPosition().getV1()
                - this.getBodyComponent(this.getEntity()).getPositionPrevious().getV1();
        final double v1yi = this.getBodyComponent(this.getEntity()).getPosition().getV2()
                - this.getBodyComponent(this.getEntity()).getPositionPrevious().getV2();
        final double v2xi = this.getBodyComponent(event.getSourceEntity()).getPosition().getV1()
                - this.getBodyComponent(event.getSourceEntity()).getPositionPrevious().getV1();
        final double v2yi = this.getBodyComponent(event.getSourceEntity()).getPosition().getV2()
                - this.getBodyComponent(event.getSourceEntity()).getPositionPrevious().getV2();

        final double sumx = v1xi + v2xi;
        final double sumy = v1yi + v2yi;

        final double v2f = (sumx - sumy) / (Math.sin(b) - Math.cos(b) * Math.sin(a));
        final double v1f = (sumx - v2f * Math.cos(b)) / Math.cos(a);

        final double v1x = v1f * Math.cos(a), v1y = v1f * Math.sin(a);
        final double v2x = v2f * Math.cos(b), v2y = v2f * Math.sin(b);

        getEntity().postEvent(new MoveEvent(getEntity(), v1x, v1y, 0));
    }

    private double getAngle(final double deltaX, final double deltaY) {
        return Math.atan2(deltaY, deltaX) * 180.0 / Math.PI;
    }

    private BodyComponent getBodyComponent(final Entity e) {
        if (e.getComponent(BodyComponent.class).isPresent()) {
            return ((BodyComponent) e.getComponent(BodyComponent.class).get());
        } else {
            throw new IllegalStateException();
        }
    }

}
