package view.javafx.game;

import javafx.scene.canvas.Canvas;

/**
 * Main view of the game, the controller uses this to manage the entityViews status and position.
 */

public interface GameView {

    /**
     * This method allows the GameMenu to set the Canvas.
     * @param cv the Canvas to draw on.
     */
    void setCanvas(Canvas cv);
    /**
     * Add an {@link EntityView} to draw.
     * @param entity {@link EntityView}
     */
    void addEntity(EntityView entity);

    /**
     * Removes an {@link EntityView} from the drawing list.
     * @param entity {@link EntityView}
     */
    void removeEntity(EntityView entity);

    /**
     * Add a {@link StatisticView} to draw.
     * @param s {@link StatisticView}
     */
    void addStatistic(StatisticView s);

    /**
     * Removes a {@link StatisticView} from the drawing list.
     * @param s {@link StatisticView}
     */
    void removeStatistic(StatisticView s);

    /**
     * This method must be called by the controller for each of the actual entityViews displayed in the canvas.
     *@param status what isaac is doing
     * @param x position on the x axis
     * @param y position on the y axis
     * @param height of a sprite
     * @param width of a sprite
     * @param entity the entityView to which this parameters must be applied
     */
    void setEntityViewParameters(EntityView entity, String status, double x, double y, double height, double width);

    /**
     * This method must be called by the controller to set the Number of each statistic.
     * @param s the statisticView that needs to be update
     * @param itemNumber the number of items that needs to be set
     */
    void setStatisticNumber(StatisticView s, double itemNumber);

    /**
     * It draws all entities in the canvas.
     */
    void draw();
}
