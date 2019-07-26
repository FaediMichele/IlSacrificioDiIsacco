package view.javafx.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Common methods to all the entity views.
 */
public interface EntityView {
    /**
     * 
     * @param image initial image
     * @param height of a sprite
     * @param width of a sprite
     * @return resized image
     */
    Image resize(Image image, double height, double width);

    /**
     * Draws the correct animation in the correct position of the canvas.
     * @param gc where to draw
     */
    void draw(GraphicsContext gc);

    /**
     * @return the x value (position on the x axis)
     */
    double getX();

    /**
     * 
     * @param x that is goind to be set (position on the x axis)
     */
    void setX(double x);

    /**
     * 
     * @return the y value (position on the y axis)
     */
    double getY();

    /**
     * 
     * @param y that is going to be set (position on the y axis)
     */
    void setY(double y);

    /**
     * 
     * @return the height value of the sprite
     */
    double getHeight();

    /**
     * 
     * @param height that is going to be set for the sprite
     */
    void setHeight(double height);

    /**
     * 
     * @return the width of the sprite
     */
    double getWidth();

    /**
     * 
     * @param width that is goind to be set for the sprite
     */
    void setWidth(double width);

    /**
     * 
     * @return the status (what the entity is doing)
     */
    String getStatus();

    /**
     * 
     * @param status that is going to be set (what the entity is doing)
     */
    void setStatus(String status);
}
