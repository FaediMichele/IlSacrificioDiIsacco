package view.javafx.game;

import java.util.Optional;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.enumeration.MovementEnum;
import model.enumeration.UpgradeEnum;

/**
 * Common methods to all the entity views.
 */
public interface EntityView {
    /**
     * 
     * @param image  initial image
     * @param height of a sprite
     * @param width  of a sprite
     * @return resized image
     */
    Image resize(Image image, double height, double width);

    /**
     * Draws the correct animation in the correct position of the canvas.
     * 
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
     * @return this
     */
    EntityView setX(double x);

    /**
     * 
     * @return the y value (position on the y axis)
     */
    double getY();

    /**
     * 
     * @param y that is going to be set (position on the y axis)
     * @return this
     */
    EntityView setY(double y);

    /**
     * 
     * @return the height value of the sprite
     */
    double getHeight();

    /**
     * 
     * @param height that is going to be set for the sprite
     * @return this
     */
    EntityView setHeight(double height);

    /**
     * 
     * @return the width of the sprite
     */
    double getWidth();

    /**
     * 
     * @param width that is goind to be set for the sprite
     * @return this
     */
    EntityView setWidth(double width);

    /**
     * 
     * @return the status (what the entity is doing)
     */
    Optional<String> getStatus();

    /**
     * 
     * @param status that is going to be set (what the entity is doing)
     * @return this
     */
    EntityView setStatus(String status);

    /**
     * Method for the animation of the normal status.
     * 
     * @param move the enum for the movement of the entity
     */
    void normal(MovementEnum move);

    /**
     * Method for the animation of the damaging status.
     * 
     * @param move the enum for the movement of the entity
     */
    void damaging(MovementEnum move);

    /**
     * Method for the animation of the dead status.
     * 
     * @param move the enum for the movement of the entity
     */
    void dead(MovementEnum move);

    /**
     * Method for the upgrade sprite.
     * 
     * @param upgrade the enum for the upgrade of the entity
     */
    void upgrade(UpgradeEnum upgrade);
}
