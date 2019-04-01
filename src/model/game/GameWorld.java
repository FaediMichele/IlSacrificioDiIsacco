package model.game;

import model.entity.Entity;

/**
 * The interface for the whole game.
 */
public interface GameWorld {
  /**
   * Gets the {@link Entity} of the player.
   * @return the player
   */
  Entity getPlayer();

  /**
   * Gets the current active {@link Floor}, so where the player is in.
   * @return the active floor
   */
  Object getActiveFloor();
}
