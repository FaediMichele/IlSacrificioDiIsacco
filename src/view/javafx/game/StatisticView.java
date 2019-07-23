package view.javafx.game;

import javafx.scene.canvas.GraphicsContext;

/**
 * Common interfaces for the views of the player's state and inventory.
 */
public interface StatisticView {
    /**
     * @return number of items for this statistic
     */
    double getNumber();

    /**
     * 
     * @param number items for this statistic
     */
    void setNumber(double number);

    /**
     * Draws the statistic on the upper right corner of the canvas.
     * @param gc the graphic contenxt to draw in
     */
    void draw(GraphicsContext gc);
}
