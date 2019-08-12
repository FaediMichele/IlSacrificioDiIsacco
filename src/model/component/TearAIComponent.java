package model.component;

import com.google.common.eventbus.Subscribe;

import model.component.mentality.AbstractMentalityComponent;
import model.entity.Entity;
import model.enumeration.BasicStatusEnum;
import model.events.CollisionEvent;
import util.EventListener;

/**
 * This component manages all the movements and actions that the tear must do
 * independently once generated.
 */
public class TearAIComponent extends AbstractComponent<TearAIComponent> {

    /**
     * the listener of this component handles the disappearance of the entity when
     * it collides with something.
     * @param entity this entity
     */
    public TearAIComponent(final Entity entity) {
        super(entity);

        this.registerListener(new EventListener<CollisionEvent>() {
            @Subscribe
            @Override
            public void listenEvent(final CollisionEvent event) {
                if (getEntity().hasComponent(AbstractMentalityComponent.class)
                        && event.getSourceEntity().hasComponent(AbstractMentalityComponent.class)
                        && getEntity().getComponent(AbstractMentalityComponent.class)
                        .get().canCollide(event.getSourceEntity().getComponent(AbstractMentalityComponent.class).get().getClass())) {
                    getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
                    getEntity().getRoom().deleteEntity(getEntity());
                }
            }
        });
    }
}
