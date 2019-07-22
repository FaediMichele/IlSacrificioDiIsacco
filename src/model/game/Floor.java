package model.game;

import java.util.Set;

import model.entity.Entity;

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
     * Change the position to the room.
     * 
     * @param index the index of the room to go
     */
    void changeRoom(Integer index);

    /**
     * Get all the room that the floor have.
     * 
     * @return Set of all the room
     */
    Set<Room> getRooms();

    /**
     * Generate all the room for the floor. Including the boss room, treasure room,
     * etc
     */
    void generateRooms();

    /**
     * Update the floor (run a frame).
     * 
     * @param deltaTime time passed from the last call.
     */
    void update(Double deltaTime);

    /**
     * React to the collision that is found at this time. 
     */
    void calculateCollision();

    /**
     * Change the position of an entity to another room.
     * @param e the entity to change the position.
     * @param location the room where the entity is.
     * @param destination the room to position the entity.
     */
    void changeEntityRoom(Entity e, Integer location, Integer destination);
}
