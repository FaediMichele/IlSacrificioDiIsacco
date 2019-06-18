package model.component;

import java.util.List;

import model.entity.Entity;
import model.events.CollisionEvent;
import model.events.EventListener;
import util.Pair;
import util.Triplet;

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
        final Entity me = this.getEntity(), other = event.getSourceEntity();
        final BodyComponent meBody = (BodyComponent) me.getComponent(BodyComponent.class).get();
        final BodyComponent otherBody = (BodyComponent) other.getComponent(BodyComponent.class).get();
        final Pair<Triplet<Double, Double, Double>, Triplet<Double, Double, Double>> vectorMoveMe = new Pair<Triplet<Double, Double, Double>, Triplet<Double, Double, Double>>(
                meBody.getPositionPrevious(), meBody.getPosition());
        final Pair<Triplet<Double, Double, Double>, Triplet<Double, Double, Double>> vectorMoveOther = new Pair<Triplet<Double, Double, Double>, Triplet<Double, Double, Double>>(
                otherBody.getPositionPrevious(), otherBody.getPosition());
    }

}
