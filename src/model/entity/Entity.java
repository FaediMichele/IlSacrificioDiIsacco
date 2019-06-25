package model.entity;

import java.util.List;
import java.util.Optional;

import model.component.Component;
import model.component.StatusComponent;
import model.events.Event;
import model.game.Room;
import util.EventListener;

/**
 * The main interface for all the entities such as enemies, items and the player
 * itself.
 */
public interface Entity {
    /**
     * Attaches a {@link Component} to the Entity to describe its behavior.
     * 
     * @param c {@link Component} to attach to the Entity
     * @return this Entity.
     */
    Entity attachComponent(Component c);

    /**
     * Detaches a {@link Component} from the Entity.
     * 
     * @param c {@link Component} to detach from the Entity
     */
    void detachComponent(Component c);

    /**
     * Register the listener on the EventBus for the event objects.
     * 
     * @param eventListener the listener
     */
    void registerListener(EventListener<? extends Event> eventListener);

    /**
     * Unregister the listener on the EventBus.
     * 
     * @param eventListener the listener
     */
    void unregisterListener(EventListener<? extends Event> eventListener);

    /**
     * Trigger the event.
     * 
     * @param event the event
     */
    void postEvent(Event event);

    /**
     * Updates the Entity and all of its {@link Component}.
     * 
     * @param deltaTime time passed from last update
     */
    void update(Double deltaTime);

    /**
     * Checks if the Entity has a certain kind of {@link Component}.
     * 
     * @param c {@link Component} to search
     * @return true if the entity has the {@link Component} else false
     */
    boolean hasComponent(Class<? extends Component> c);

    /**
     * Gets the certain kind of {@link Component} from Entity.
     * 
     * @param c {@link Component} to search
     * @return the {@link Component}
     */
    Optional<? extends Component> getComponent(Class<? extends Component> c);

    /**
     * Return the Set of all {@link Component}.
     * 
     * @return the {@link Set}
     */
    List<Component> getComponents();

    /**
     * Get the room  where this entity is.
     * @return the room.
     */
    Room getRoom();

    /**
     * Change the room where this entity is.
     * @param r the new room
     */
    void changeRoom(Room r);

    /**
     * @return the statusComponent of the entity, 
     * this utility is useful because all entities need to log their status very often
     */
    StatusComponent getStatusComponent();
}
