package model.component;

import java.util.List;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.entity.Tear;
import model.events.CollisionEvent;
import model.events.DamageEvent;
import model.events.EventListener;
import model.events.PickUpEvent;

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

    }

    /**
     * Method which is called when a collision occurs, this method must ONLY handle
     * the damage.
     * 
     * @param event is the collision event
     */
    protected void collisionManagement(final CollisionEvent event) {
        AbstractMentalityComponent sourceMentaliy;
        AbstractMentalityComponent myMentality;

        if (event.getSourceEntity().getComponent(AbstractMentalityComponent.class).isPresent()) {
            sourceMentaliy = (AbstractMentalityComponent) event.getSourceEntity()
                    .getComponent(AbstractMentalityComponent.class).get();
        } else {
            sourceMentaliy = new NeutralMentalityComponent(event.getSourceEntity());
        }

        if (getEntity().getComponent(AbstractMentalityComponent.class).isPresent()) {
            myMentality = (AbstractMentalityComponent) event.getSourceEntity()
                    .getComponent(AbstractMentalityComponent.class).get();
        } else {
            myMentality = new NeutralMentalityComponent(event.getSourceEntity());
        }

        if (sourceMentaliy.isDamageableByMe(myMentality.getClass())) {
            if (myMentality.canHurtMe(sourceMentaliy.getClass())) {
                getEntity().postEvent(new DamageEvent(event.getSourceEntity()));
            }
        }

    }

    /**
     * This method is called when the entity collides with entities and must manage
     * ONLY if this entity must be collected or not.
     * 
     * @param event is the collision event
     */
    protected void collectibleManagement(final CollisionEvent event) {
        if (event.getSourceEntity().hasComponent(AbstractPickupableComponent.class)) {
            getEntity().postEvent(new PickUpEvent(event.getSourceEntity()));
        }
    }

}
