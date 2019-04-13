package model.component;

import model.entity.Entity;

/**
 * This represent a single part that composes an Entity.
 *
 */

public interface Component {

    /**
     * Update the component changing its statistics, must be done once a frame.
     * 
     * @param deltaTime time elapsed since the last call, in milliseconds.
     */
    void update(Double deltaTime);

    /**
     * Get the entity this component is attached to.
     * 
     * @return {@link Entity}.
     */
     Entity getEntity();
}
