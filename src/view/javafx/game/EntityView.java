package view.javafx.game;

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
    Image resize(Image image, int height, int width);
}
