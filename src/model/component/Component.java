package model.component;

import model.entity.Entity;

/**
 * This represent a single part that composes an Entity.
 *
 */

public interface Component {
    /**
     * Check if the component is active, this has to be done before each update call.
     * @return {@link Boolean}.
     */
    boolean isActive();// protected

    /**
     * Enable or disable a component by setting a variable. 
     * @param state {@link Boolean}.
     */
    void setState(boolean state);// protected

    /**
     * Update the component changing its statistics, must be done once a frame.
     * @param deltaTime time elapsed since the last call, in milliseconds.
     */
    void update(Double deltaTime);

    /**
     * Get the entity this component is attached to.
     * @return {@link Entity}.
     */
    Entity getEntity();// protected

    /**
     * Sets the entity this component has to be attached to.
     * @param e {@link Entity}
     */
    void setEntity(Entity e);// protected

    /**
     * Initialize the component.
     */
    void init();// protected

    /**
     * Release all resources used by this component.
     */
    void dispose();// protected
    @Override
    boolean equals(Object component);
}
