package model.game;

import java.util.Set;

import model.entity.Door;
import model.entity.Entity;

/**
 * The interface for the rooms for the floor. It have enemy and environment
 * entity.
 */
public interface Room {
    /**
     * Get all the {@link Entity} that the room contains.
     * 
     * @return Set of all {@link Entity}.
     */
    Set<? extends Entity> getEntity();

    /**
     * Get the door that the room have.
     * 
     * @return Set of the Door.
     */
    Set<? extends Door> getDoor();

    /**
     * Update all the {@link Entity} (run a frame).
     * 
     * @param deltaTime time that has passed between the last update.
     */
    void updateEntity(Double deltaTime);

    /**
     * React to the collision that is found at this time. 
     */
    void calculateCollision();

    /**
     * If this room has been completed (the player killed all the enemy).
     * 
     * @return true if the room is completed.
     */
    boolean completed();

    /**
     * Get the index of the room.
     * 
     * @return the index of the room.
     */
    int getIndex();

    /**
     * Add an {@link Entity} to the room.
     * 
     * @param e the {@link Entity}
     */
    void insertEntity(Entity e);

    /**
     * Delete the {@link Entity} to the room.
     * @param e the {@link Entity} to delete
     */
    void deleteEntity(Entity e);

    /**
     * Get the {@link Floor} that contains the room.
     * @return the {@link Floor} where the room is.
     */
    Floor getFloor();


    /**
     * Set the {@link Floor} that contains the room.
     * @param f the {@link Floor} where the room is
     */
    void setFloor(Floor f);
}
