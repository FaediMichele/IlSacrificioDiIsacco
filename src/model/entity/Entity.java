package model.entity;

import java.util.Optional;

/**
 * The main interface for all the entities such as enemies, items and the player itself.
 */
public interface Entity {
    /**
     * Attaches a {@link Component} to the Entity to describe its behavior.
     * @param c {@link Component} to attach to the Entity
     */
    void attach(Object c);

    /**
     * Detaches a {@link Component} from the Entity.
     * @param c {@link Component} to detach from the Entity
     */
    void detach(Object c);

    /**
     * Register the listener on the EventBus for the event objects.
     * @param eventListener the listener
     */
    void register(Object eventListener);

    /**
     * Unregister the listener on the EventBus.
     * @param eventListener the listener
     */
    void unregister(Object eventListener);

    /**
     * Trigger the event.
     * @param event the event
     */
    void post(Object event);

    /**
     * Updates the Entity and all of its {@link Component}.
     */
    void update();

    /**
     * Checks if the Entity has a certain kind of {@link Component}. 
     * @param c {@link Component} to search
     * @return true if the entity has the {@link Component} else false
     */
    boolean has(Class<? extends Object> c);

    /**
     * Gets the certain kind of {@link Component} from Entity. 
     * @param c {@link Component} to search
     * @return the {@link Component}
     */
    Optional<Object> get(Class<? extends Object> c);

    /**
     * Gets the current {@link PositionComponent} of the Entity.
     * @return the {@link PositionComponent} of the Entity
     */
    Object getPosition();

    /**
     * Gets the {@link CollisionComponent} of the Entity.
     * @return the {@link CollisionComponent}
     */
    Object getCollision();
}
