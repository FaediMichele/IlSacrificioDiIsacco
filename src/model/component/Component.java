package model.component;

import model.Entity;

/**
 * This represent a single part that composes an Entity.
 *
 */

public interface Component {
    /**
     * Check if the component is active, this has to be done before each update call.
     * @return {@link Boolean}.
     */
    boolean isActive();

    /**
     * Enable or disable a component by setting a variable. 
     * @param state {@link Boolean}.
     */
    void setState(boolean state);

    /**
     * Update the component changing its statistics, must be done once a frame.
     * @param deltaTime time elapsed since the last call, in milliseconds.
     */
    void update(Double deltaTime);

    /**
     * Get the entity this component is attached to.
     * @return {@link Entity}.
     */
    Entity getEntity();

    /**
     * Initialize the component.
     */
    void init();

    /**
     * Release all resources used by this component.
     */
    void dispose();
}
