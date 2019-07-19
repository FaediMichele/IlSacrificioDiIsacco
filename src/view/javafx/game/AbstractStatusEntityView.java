package view.javafx.game;

import javafx.scene.canvas.GraphicsContext;

/**
 * Abstract class for entityViews that have different statuses and need to implement this function to draw the correct one.
 */
public abstract class AbstractStatusEntityView extends AbstractEntityView {

    AbstractStatusEntityView(final GameView gv) {
        super(gv);
    }

    AbstractStatusEntityView() {
        super();
    }
    /**
     * Draws the correct animation in the correct position of the canvas.
     * @param gc where to draw
     * @param status what isaac is doing
     * @param x position on the x axis
     * @param y position on the y axis
     * @param height of a sprite
     * @param width of a sprite
     */
    public abstract void draw(GraphicsContext gc, String status, int x, int y, int height, int width);

}
