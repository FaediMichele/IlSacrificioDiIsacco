package model.component;

import model.entity.Entity;

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
    }

    /**
     * Sets the remaining life of the fire after taking damage.
     * @param lifeLost the life lost
     */
    public void changeLife(final Integer lifeLost) {
        this.lifeLeft = (lifeLost > this.lifeLeft) ? 0 : this.lifeLeft - lifeLost;
    }
}
