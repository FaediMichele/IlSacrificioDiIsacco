package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.entity.events.CollisionEvent;
import model.entity.events.DamageEvent;
import model.entity.events.EventListener;
import model.entity.events.PickUpEvent;

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
                Mentality myMentality = Mentality.NEUTRAL;
                Mentality oposedMentality = Mentality.NEUTRAL;
                if (event.getSourceEntity().hasComponent(MentalityComponent.class)) {
                    myMentality = ((MentalityComponent) getEntity().getComponent(MentalityComponent.class).get())
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

                if (event.getSourceEntity().hasComponent(PickUpComponent.class)) {
                    if (((PickUpComponent) event.getSourceEntity().getComponent(PickUpComponent.class).get()).isCollectible()) {
                        getEntity().postEvent(new PickUpEvent(event.getSourceEntity()));
                    }
                }
            }
        });
    }
}
