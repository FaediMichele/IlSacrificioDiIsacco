package view.javafx.game;

import java.util.Optional;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.enumeration.BasicStatusEnum;
import model.enumeration.MovementEnum;

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
    Optional<BasicStatusEnum> getStatus();

    /**
     * 
     * @param status that is going to be set (what the entity is doing)
     * @return this
     */
    EntityView setStatus(BasicStatusEnum status);

    /**
     * @param gameView the game view to which this EntityView belongs
     */
    void setGameView(GameView gameView);

    /**
     * Method for the animation of the default status.
     * 
     * @param move the enum for the movement of the entity
     */
    void def(MovementEnum move);

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
     * Method for the animation of the triggered status.
     * 
     * @param move the enum for the movement of the entity
     */
    void triggered(MovementEnum move);

    /**
     * Method for the animation of the exploded status.
     * 
     * @param move the enum for the movement of the entity
     */
    void exploded(MovementEnum move);

    /**
     * Method for the animation of the pick up status.
     * 
     * @param move the enum for the movement of the entity
     */
    void pickUp(MovementEnum move);

    /**
     * Method for the animation of the appear status.
     * 
     * @param move the enum for the movement of the entity
     */
    void appear(MovementEnum move);

    /**
     * Method for the animation of the disappear status.
     * 
     * @param move the enum for the movement of the entity
     */
    void disappear(MovementEnum move);

    /**
     * Method for the animation of the full status.
     * 
     * @param move the enum for the movement of the entity
     */
    void full(MovementEnum move);

    /**
     * Method for the animation of the half status.
     * 
     * @param move the enum for the movement of the entity
     */
    void half(MovementEnum move);
}
