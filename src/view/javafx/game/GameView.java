package view.javafx.game;

import java.util.List;

import model.enumeration.HeartEnum;
import util.Pair;

/**
 * Main view of the game, the controller uses this to manage the entityViews
 * status and position.
 */

public interface GameView {
    /**
     * Add an {@link EntityView} to draw.
     * 
     * @param entity {@link EntityView}
     */
    void addEntity(EntityView entity);

    /**
     * Removes an {@link EntityView} from the drawing list.
     * 
     * @param entity {@link EntityView}
     */
    void removeEntity(EntityView entity);

    /**
     * Set the current {@link RoomView}.
     * 
     * @param room {@link RoomView}
     */
    void setRoomView(RoomView room);

    /**
     * Add a {@link StatisticView} to draw.
     * 
     * @param s {@link StatisticView}
     */
    void addStatistic(StatisticView s);

    /**
     * Removes a {@link StatisticView} from the drawing list.
     * 
     * @param s {@link StatisticView}
     */
    void removeStatistic(StatisticView s);

    /**
     * @return a Copy of the statistics list
     */
    List<StatisticView> getStatistics();

    /**
     * This method must be called by the controller to set the Number of each
     * statistic.
     * 
     * @param s          the statisticView that needs to be update
     * @param itemNumber the number of items that needs to be set
     */
    void setNumberStatistic(StatisticView s, double itemNumber);

    /**
     * Sets the list of HeartViews in the statistics.
     * 
     * @param s 
     * @param hearts 
     */
    void setHeartsStatistic(StatisticView s, List<Pair<HeartEnum, Double>> hearts);

    /**
     * Return to the initial state.
     */
    void clear();

    /**
     * It draws all entities in the canvas.
     */
    void draw();

    /**
     * 
     * @return the width of the canvas
     */
    double getWidth();

    /**
     * 
     * @return the height of the canvas
     */
    double getHeight();
}
