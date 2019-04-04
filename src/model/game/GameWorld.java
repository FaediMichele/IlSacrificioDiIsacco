package model.game;

import java.util.List;

import model.entity.Player;

/**
 * The interface for the whole game.
 */
public interface GameWorld {
    /**
     * Gets {@link Player}.
     * 
     * @return the player
     */
    Player getPlayer();

    /**
     * Gets the List of {@link Floor}.
     * 
     * @return the floors
     */
    List<Floor> getFloors();

    /**
     * Gets the current active {@link Floor}, so where the player is in.
     * 
     * @return the active floor
     */
    Floor getActiveFloor();
}
