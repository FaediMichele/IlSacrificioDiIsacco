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
     * If this room has been completed ( the player kill all the enemy).
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
}
