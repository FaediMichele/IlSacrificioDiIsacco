package model.game;

import java.util.List;
import java.util.PriorityQueue;

import model.entity.Player;
import model.util.EntityInformation;
import model.util.StatisticsInformations;
import util.Command;
import util.EventListener;

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
     * Adds a {@link Floor}.
     * 
     * @param floor the floor
     */
    void addFloor(Floor floor);

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

    /**
     * Get the set of the entity that will be updated.
     * @return the set.
     */
    List<EntityInformation> getEntityInformation();

    /**
     * 
     * @return true if the floor is change from last update
     */
    boolean isChangeFloor();

    /**
     * 
     * @return true if the floor is change from last update
     */
    boolean isChangeRoom();

    /**
     * Go to the next frame of the game.
     * 
     * @param deltaTime the time that has passed from the last call.
     * @return if the model has finished to run (game over).
     */
    boolean update(double deltaTime);

    /**
     * Sets the active {@link Floor}.
     * 
     * @param activeFloor the active floor index
     */
    void setActiveFloor(Integer activeFloor);

    /**
     * Add a listener for changes that can be useful from the outside.
     * 
     * @param eventListener the {@link EventListener} to add.
     */
    void registerListener(EventListener<?> eventListener);

    /**
     * remove a listener for changes of the game.
     * 
     * @param eventListener the {@link EventListener} to remove.
     */
    void unregisterListener(EventListener<?> eventListener);

    /**
     * Pass the input to the model.
     * @param c the list of {@link Command}
     */
    void input(PriorityQueue<Command> c);

    /**
     * 
     * @return statistics informations for player.
     */
    StatisticsInformations getStatistics();
}
