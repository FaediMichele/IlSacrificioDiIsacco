package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
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
                /**
                 * damage management
                 */
                Mentality myMentality = Mentality.NEUTRAL;
                Mentality oppositeMentality = Mentality.NEUTRAL;
                if (event.getSourceEntity().hasComponent(MentalityComponent.class)) {
                    myMentality =       ((MentalityComponent) getEntity()
                            .getComponent(MentalityComponent.class).get()).getMentality();
                    oppositeMentality =  ((MentalityComponent) event.getSourceEntity()
                            .getComponent(MentalityComponent.class).get()).getMentality();
                }

                if ((myMentality.equals(Mentality.EVIL) && oppositeMentality.equals(Mentality.GOOD))
                        || (myMentality.equals(Mentality.GOOD) && oppositeMentality.equals(Mentality.EVIL))
                        || (oppositeMentality.equals(Mentality.PSYCHO) && !myMentality.equals(Mentality.NEUTRAL))) {
                    getEntity().postEvent(new DamageEvent(event.getSourceEntity()));
                }

                /**
                 * management collect an object
                 */
                if (event.getSourceEntity().hasComponent(AbstractCollectibleComponent.class)) {
                    getEntity().postEvent(new PickUpEvent(event.getSourceEntity()));
                }

//                /**
//                 * handles the collision with the door
//                 */
//                if (event.getSourceEntity().hasComponent(DoorComponent.class)) {
//                    /**
//                     * la porta è chiusa?
//                     */
//
//                    /**
//                     * ho la chiave
//                     */
//                    if (getEntity().hasComponent(KeychainComponent.class)) {
//                        if (((KeychainComponent) getEntity().getComponent(KeychainComponent.class).get())
//                                .getKey()
//                                .contains(event.getSourceEntity())) {
//                        }
//                    }
//                }
            }
        });
    }
}
