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
     * @param index the new index of the statstic in the GameView statstics list
     */
    void setIndex(int index);

    /**
     * Draws the statistic on the upper right corner of the canvas.
     * @param gc the graphic contenxt to draw in
     */
    void draw(GraphicsContext gc);

    /**
     * @return the entityClass
     */
    Class<? extends EntityView> getEntityClass();
}
