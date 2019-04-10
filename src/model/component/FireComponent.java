package model.component;

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
        this.getEntity().registerListener(new EventListener<FireHittedEvent>((event) -> {
            changeLife(1);

            if (this.lifeLeft == 0) {
                this.getEntity().postEvent(new FireOutEvent(event.getSourceEntity(), event.getSourceComponent(), this.fireType));
            }
        }));

        this.getEntity().registerListener(new EventListener<FireOutEvent>((event) -> {
            //TODO
        }));
    }

    /**
     * 
     * @return the life left
     */
    public Integer getLife() {
        return this.lifeLeft;
    }

    /**
     * Sets the remaining life of the fire after taking damage.
     * 
     * @param lifeLost the life lost
     */
    private void changeLife(final Integer lifeLost) {
        this.lifeLeft = (lifeLost > this.lifeLeft) ? 0 : this.lifeLeft - lifeLost;
    }

    @Override
    public final boolean equals(final Object component) {
        boolean equals = super.equals(component);
        equals = equals && ((FireComponent) component).getLife().equals(this.getLife());
        return equals;
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }
}
