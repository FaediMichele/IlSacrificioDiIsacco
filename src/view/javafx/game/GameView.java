package view.javafx.game;

/**
 * Main view of the game, the controller uses this to manage the entityViews status and position.
 */

public interface GameView {
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
     * It draws all entities in the canvas.
     */
    void draw();
}
