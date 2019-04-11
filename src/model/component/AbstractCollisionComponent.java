package model.component;

import com.google.common.eventbus.Subscribe;
import model.entity.Entity;
import model.entity.events.CollisionEvent;
import model.entity.events.DamageEvent;
import model.entity.events.EventListener;

/**
 * This class manages the collision of this entity with the others.
 *
 */
public abstract class AbstractCollisionComponent extends AbstractComponent {

   /**
     * 
     * In the constructor of {@link AbstractCollisionComponent} the Listener is
     * created that decides whether to generate the event damage for its own entity
     * or not.
     * 
     * @param entity the {@link Entity} to which it belongs
     */
    AbstractCollisionComponent(final Entity entity) {
        super(entity);
        this.registerListener(new EventListener<CollisionEvent>() {

            @Override
            @Subscribe
            public void listenEvent(final CollisionEvent event) {
                Mentality myMentality = Mentality.NEUTRAL;
                Mentality oposedMentality = Mentality.NEUTRAL;
                if (event.getSourceEntity().hasComponent(MentalityComponent.class)) {
                    oposedMentality = ((MentalityComponent) getEntity().getComponent(MentalityComponent.class).get())
                            .getMentality();
                }
                if (event.getSourceEntity().hasComponent(MentalityComponent.class)) {
                    oposedMentality = ((MentalityComponent) event.getSourceEntity()
                            .getComponent(MentalityComponent.class).get()).getMentality();
                }

                if ((myMentality.equals(Mentality.EVIL) && oposedMentality.equals(Mentality.GOOD))
                        || (myMentality.equals(Mentality.GOOD) && oposedMentality.equals(Mentality.EVIL))
                        || (oposedMentality.equals(Mentality.PSYCHO) && !myMentality.equals(Mentality.NEUTRAL))) {
                    getEntity().postEvent(new DamageEvent(event.getSourceEntity()));
                }
            }
        });
    }
}
