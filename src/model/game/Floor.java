package model.game;

import java.util.Set;

/**
 * Interface for the floor.
 * 
 * <p>See also {@link Room}
 */
public interface Floor {

  /**
   * Get the room where the player is.
   * @return the room where the player is
   */
  Room getActiveRoom();

  /**
   * Get all the room that the floor have.
   * @return Set of all the room
   */
  Set<Room> getRooms();


  /**
   * Generate all the room for the floor. Including the boss room, treasure room, etc
   */
  void generateRooms();
}
