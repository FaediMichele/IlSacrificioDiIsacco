package model.component;

import java.util.Objects;

import com.google.common.eventbus.Subscribe;
import model.entity.Entity;
import model.entity.Heart;
import model.events.EventListener;
import model.events.FireHittedEvent;
import model.events.FireOutEvent;

/**
 * Implements the data for the fires.
 */
public class FireAIComponent extends AbstractComponent<FireAIComponent> {

    private static final int MAX_LIFE = 4;
    private int lifeLeft;
    private final FireType fireType;

    /**
     * Initialize the entity.
     * 
     * @param entity   the source entity
     * @param fireType the {@link FireType}
     */
    public FireAIComponent(final Entity entity, final FireType fireType) {
        super(entity);
        Objects.requireNonNull(fireType);
        this.lifeLeft = MAX_LIFE;
        this.fireType = fireType;

        this.registerListener(new EventListener<FireHittedEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final FireHittedEvent event) {
                changeLife(1);

                if (getLife() == 0) {
                    getEntity().postEvent(new FireOutEvent(event.getSourceEntity(), getFireType()));
                }

            }
        });

        this.registerListener(new EventListener<FireOutEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final FireOutEvent event) {
                final FireType type = event.getFireType();
                if (type == FireType.BLUE) {
                    final Heart h = new Heart();
                    final BodyComponent fireBody = (BodyComponent) event.getSourceEntity().getComponent(BodyComponent.class).get();
                    final BodyComponent newBody = BodyComponent.class.cast(h.getComponent(BodyComponent.class).get());
                    newBody.setPosition(fireBody.getPosition());
                    h.attachComponent(newBody);
                    event.getSourceEntity().getRoom().insertEntity(h);
                    System.out.println("BLUE");
                }
                event.getSourceEntity().getRoom().deleteEntity(event.getSourceEntity());
            }
        });
    }

    /**
     * 
     * @return the life left
     */
    public Integer getLife() {
        return this.lifeLeft;
    }

    /**
     * @return the fireType
     */
    public FireType getFireType() {
        return this.fireType;
    }

    /**
     * Sets the remaining life of the fire after taking damage.
     * 
     * @param lifeLost the life lost
     */
    private void changeLife(final Integer lifeLost) {
        this.lifeLeft = (lifeLost > this.lifeLeft) ? 0 : this.lifeLeft - lifeLost;
    }
}
