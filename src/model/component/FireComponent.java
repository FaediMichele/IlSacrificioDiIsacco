package model.component;

import com.google.common.eventbus.Subscribe;
import model.entity.Entity;
import model.entity.events.EventListener;
import model.entity.events.FireHittedEvent;
import model.entity.events.FireOutEvent;

/**
 * Implements the data for the fires.
 */
public class FireComponent extends AbstractComponent {

    private static final int MAX_LIFE = 4;
    private int lifeLeft;
    private final FireType fireType;

    /**
     * Initialize the entity.
     * 
     * @param entity   the source entity
     * @param fireType the {@link FireType}
     */
    public FireComponent(final Entity entity, final FireType fireType) {
        super(entity);
        this.lifeLeft = MAX_LIFE;
        this.fireType = fireType;

        getEntity().registerListener(new EventListener<FireHittedEvent>() {

            @Override
            @Subscribe
            public void listenEvent(final FireHittedEvent event) {
                changeLife(1);

                if (getLife() == 0) {
                    getEntity().postEvent(
                            new FireOutEvent(event.getSourceEntity(), event.getSourceComponent(), getFireType()));
                }

            }
        });

        getEntity().registerListener(new EventListener<FireOutEvent>() {

            @Override
            @Subscribe
            public void listenEvent(final FireOutEvent event) {
                final FireType type = event.getFireType();
                if (type == FireType.RED) {
                    System.out.println("RED");
                } else if (type == FireType.BLUE) {
                    System.out.println("BLUE");
                } else {
                    System.out.println("Other");
                }
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
        return fireType;
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
