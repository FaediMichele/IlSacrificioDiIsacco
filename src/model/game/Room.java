package model.game;
import java.util.Set;

import model.entity.Entity;

/**
 * The interface for the rooms for the floor. It have enemy and environment entity.
 */
public interface Room {
  /**
   * Get all the {@link Entity} that the room contains.
   * @return Set of all {@link Entity}.
   */
  Set<Entity> getEntity();

  /**
   * Get the door that the room have.
   * @return Set of the Door.
   */
  Set<Entity> getDoor();

  /**
   * Update all the {@link Entity} (run a frame).
   * @param deltaTime time that has passed between the last update.
   */
  void updateEntity(Double deltaTime);

  /**
   * If this room is currently active.
   * @return true if the player is in this room.
   */
  boolean isActive();

  /**
   * Set the room in active state.
   * @param state the state
   */
  void setActive(boolean state);

  /**
   * If this room has been completed ( the player kill all the enemy).
   * @return true if the room is completed.
   */
  boolean isComplete();
}
