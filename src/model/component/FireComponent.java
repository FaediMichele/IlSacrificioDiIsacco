package model.component;

import model.entity.Entity;
import model.entity.events.EventListener;
import model.entity.events.FireHittedEvent;

/**
 * Implements the data for the fires.
 */
public class FireComponent extends AbstractComponent {

    private static final int MAX_LIFE = 4;
    private int lifeLeft;

    /**
     * Initialize the entity.
     * @param entity the source entity
     */
    public FireComponent(final Entity entity) {
        super(entity);
        this.lifeLeft = MAX_LIFE;
        this.getEntity().registerListener(new EventListener<FireHittedEvent>(() -> {
            changeLife(1);
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
     * @param lifeLost the life lost
     */
    public void changeLife(final Integer lifeLost) {
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
