package view.javafx.game;

import java.util.Optional;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * This class contains the method animate that can be used by all the entity view 
 * after having extracted the sprites from the sheet set the right list in entityActualSprites.
 */
public abstract class AbstractEntityView implements EntityView {
    private final Optional<GameViewImpl> gameView;

    private double x;
    private double y;
    private double height;
    private double width;
    private String status;

    AbstractEntityView(final GameViewImpl gv) {
        super();
        this.gameView = Optional.of(gv);
    }

    AbstractEntityView() {
        super();
        this.gameView = Optional.empty();
    }

    /**
     * 
     * @return the GameView to which the entityView is attached (if there is one);
     */
    public Optional<GameViewImpl> getGameView() {
        return gameView;
    }

    /**
     * {@inheritDoc}
     */
    public Image resize(final Image image, final double height, final double width) {
        final ImageView resizedImage = new ImageView(image);
        resizedImage.setFitHeight(height);
        resizedImage.setFitWidth(width);
        return resizedImage.snapshot(null, null);
    }

    /**
     * 
     * @return the x value (position on the x axis)
     */
    public double getX() {
        return x;
    }

    /**
     * 
     * @param x that is goind to be set (position on the x axis)
     */
    public void setX(final double x) {
        this.x = x;
    }

    /**
     * 
     * @return the y value (position on the y axis)
     */
    public double getY() {
        return y;
    }

    /**
     * 
     * @param y that is going to be set (position on the y axis)
     */
    public void setY(final double y) {
        this.y = y;
    }

    /**
     * 
     * @return the height value of the sprite
     */
    public double getHeight() {
        return height;
    }

    /**
     * 
     * @param height that is going to be set for the sprite
     */
    public void setHeight(final double height) {
        this.height = height;
    }

    /**
     * 
     * @return the width of the sprite
     */
    public double getWidth() {
        return width;
    }

    /**
     * 
     * @param width that is goind to be set for the sprite
     */
    public void setWidth(final double width) {
        this.width = width;
    }

    /**
     * 
     * @return the status (what the entity is doing)
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status that is going to be set (what the entity is doing)
     */
    public void setStatus(final String status) {
        this.status = status;
    }
}
