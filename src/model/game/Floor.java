package model.game;

import java.util.List;

import model.entity.Entity;
import util.EventListener;

/**
 * Interface for the floor.
 * 
 * <p>
 * See also {@link Room}
 */
public interface Floor {

    /**
     * Get the room where the player is.
     * 
     * @return the room where the player is
     */
    Room getActiveRoom();

    /**
     * Get all the room that the floor have.
     * 
     * @return Set of all the room
     */
    List<Room> getRooms();

    /**
     * Update the floor (run a frame).
     * 
     * @param deltaTime time passed from the last call.
     */
    void update(Double deltaTime);

    /**
     * Change the position of an entity to another room.
     * @param e the entity to change the position.
     * @param location the room where the entity is.
     * @param destination the room to position the entity.
     */
    void changeEntityRoom(Entity e, Integer location, Integer destination);

    /**
     * Add a listener for changes that can be useful from the outside.
     * @param eventListener the {@link EventListener} to add.
     */
    void registerListener(EventListener<?> eventListener);

    /**
     * remove a listener for changes of the {@link Floor}.
     * @param eventListener the {@link EventListener} to remove.
     */
    void unregisterListener(EventListener<?> eventListener);

    /**
     * 
     * @return true if the room is change from the last upadte.
     */
    boolean isChangeRoom();
}
