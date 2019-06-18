package model.component;

import java.util.List;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.events.CollisionEvent;
import model.events.DamageEvent;
import model.events.EventListener;
import model.events.PickUpEvent;
import util.Pair;
import util.Triplet;

/**
 * This class manages the collision of this entity with the others.
 *
 */

public class CollisionComponent extends AbstractComponent<CollisionComponent> {

    /**
     * Default CollisionComponent constructor.
     * 
     * @param entity entity for this component
     */
    public CollisionComponent(final Entity entity) {
        super(entity);
        this.registerListener(new EventListener<CollisionEvent>() {

            @Override
            @Subscribe
            public void listenEvent(final CollisionEvent event) {
                handleCollision(event);
            }
        });
    }

    /**
     * Custom event Listener.
     * 
     * @param entity         the {@link Entity}
     * @param eventListeners the {@link EventListener}
     */
    public CollisionComponent(final Entity entity, final List<EventListener<CollisionEvent>> eventListeners) {
        super(entity);
        eventListeners.forEach(e -> this.registerListener(e));
    }

    /**
     * 
     * @param event is the collision event
     */
    protected void handleCollision(final CollisionEvent event) {
        final Entity me = this.getEntity(), other = event.getSourceEntity();
        final BodyComponent meBody = (BodyComponent) me.getComponent(BodyComponent.class).get();
        final BodyComponent otherBody = (BodyComponent) other.getComponent(BodyComponent.class).get();
        final Pair<Triplet<Double, Double, Double>, Triplet<Double, Double, Double>> vectorMoveMe = new Pair<Triplet<Double, Double, Double>, Triplet<Double, Double, Double>>(
                meBody.getPositionPrevious(), meBody.getPosition());
        final Pair<Triplet<Double, Double, Double>, Triplet<Double, Double, Double>> vectorMoveOther = new Pair<Triplet<Double, Double, Double>, Triplet<Double, Double, Double>>(
                otherBody.getPositionPrevious(), otherBody.getPosition());

    }
}
