package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
* View of the key.
*/
public class KeyView extends AbstractEntityView {
    private static Image keySprite;

    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(KeyView.class.getResource("/gameImgs/pickup_001_heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        final int width = 16;
        final int height = 27;
        if (img != null) {
            keySprite = SwingFXUtils.toFXImage(img.getSubimage(0, 0, width, height), null);
        }
    }

    /**
     * Basic constructor.
     */
    public KeyView() {
        super();
    }

    /**
     * Draws the correct animation in the correct position of the canvas.
     * @param gc where to draw
     * @param x position on the x axis
     * @param y position on the y axis
     * @param height of a sprite
     * @param width of a sprite
     */
    public void draw(final GraphicsContext gc, final int x, final int y, final int height, final int width) {
        Image img = super.resize(keySprite, height, width);
        gc.drawImage(img, x, y);
    }
}
