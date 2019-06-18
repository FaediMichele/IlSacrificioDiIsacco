package model.component;

import java.util.List;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.entity.Tear;
import model.events.CollisionEvent;
import model.events.DamageEvent;
import model.events.EventListener;

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
        this.registerListener(new EventListener<CollisionEvent>() {

            @Override
            @Subscribe
            public void listenEvent(final CollisionEvent event) {
                /**
                 * handles the collision with a locked component
                 */
//                if (event.getSourceEntity().hasComponent(LockComponent.class)) {
//                    /**
//                     * la porta è chiusa?
//                     */
//
//                    /**
//                     * ho la chiave
//                     */
//                    if (getEntity().hasComponent(InventoryComponent.class) &&
//                            ((InventoryComponent)getEntity().getComponent(InventoryComponent.class).get()).thingsOfThisKind(Key.class))  {
//                        if (((KeychainComponent) getEntity().getComponent(KeychainComponent.class).get())
//                                .getKey()
//                                .contains(event.getSourceEntity())) {
//                        }
//                    }
//                }

                /**
                 * handles the disappearing of the tear after a collision
                 */
                if (getEntity().getClass().equals(Tear.class)) {
                    getEntity().getRoom().deleteEntity(getEntity());
                }
            }
        });
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


}
