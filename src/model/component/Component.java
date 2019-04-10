package model.component;

/**
 * This represent a single part that composes an Entity.
 *
 */

public interface Component {

    /**
     * Update the component changing its statistics, must be done once a frame.
     * @param deltaTime time elapsed since the last call, in milliseconds.
     */
    void update(Double deltaTime);

    @Override
    boolean equals(Object component);
}
