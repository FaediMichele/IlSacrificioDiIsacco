package model.component.collision;

import java.util.List;
import com.google.common.eventbus.Subscribe;
import model.component.AbstractComponent;
import model.component.mentality.AbstractMentalityComponent;
import model.component.mentality.NeutralMentalityComponent;
import model.entity.Entity;
import model.events.CollisionEvent;
import model.events.DamageEvent;
import util.EventListener;

/**
 * This class manages the collision of this entity with the others compared to
 * the damage.
 *
 */

public class CollisionComponent extends AbstractComponent {
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
        this.damageManagement(event);
    }

    /**
     * Method which is called when a collision occurs, this method must ONLY handle
     * the damage.
     * 
     * @param event is the collision event
     */
    protected void damageManagement(final CollisionEvent event) {
        AbstractMentalityComponent sourceMentaliy;
        AbstractMentalityComponent myMentality;
        if (event.getSourceEntity().getComponent(AbstractMentalityComponent.class).isPresent()) {
            sourceMentaliy = event.getSourceEntity().getComponent(AbstractMentalityComponent.class).get();
        } else {
            sourceMentaliy = new NeutralMentalityComponent();
        }
        if (getEntity().getComponent(AbstractMentalityComponent.class).isPresent()) {
            myMentality = getEntity().getComponent(AbstractMentalityComponent.class).get();
        } else {
            myMentality = new NeutralMentalityComponent();
        }
        if (sourceMentaliy.isDamageableByMe(this.getEntity().getClass()) 
                && myMentality.canHurtMe(event.getSourceEntity().getClass())) {
                this.damage(event);
            }
        }

    /**
     * Posts the DamageEvent.
     * @param event the CollisionEvent
     */
    public void damage(final CollisionEvent event) {
        getEntity().postEvent(new DamageEvent(event.getSourceEntity()));
    }

}
