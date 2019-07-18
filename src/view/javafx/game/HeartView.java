package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
* View of the hearts.
*/
public class HeartView extends AbstractEntityView {

    private static Image simpleHeart;
    private static Image halfSimpleHeart;
    private static Image blackHeart;
    private static Image halfBlackHeart;

    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(HeartView.class.getResource("/gameImgs/pickup_001_heart.png"));
            final int delta = 30;
            simpleHeart = SwingFXUtils.toFXImage(img.getSubimage(0, 0, delta, delta), null);
            halfSimpleHeart = SwingFXUtils.toFXImage(img.getSubimage(delta + 2, 0, delta, delta), null);

            final int blackY = 64;
            blackHeart = SwingFXUtils.toFXImage(img.getSubimage(0, blackY, delta, delta), null);
            halfBlackHeart = SwingFXUtils.toFXImage(img.getSubimage(delta + 2, blackY, delta, delta), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Base constructor that extract the sprites from the sheet.
     * @throws IOException trying to get the resource image
     */
    public HeartView() throws IOException {
        super();
    }

    /**
     * Draws the correct animation in the correct position of the canvas.
     * @param gc where to draw
     * @param x position on the x axis
     * @param y position on the y axis
     * @param color of the heart 
     * @param type of heart (full or half)
     * @param height of a sprite
     * @param width of a sprite
     */
    public void draw(final GraphicsContext gc, final String color, final String type, 
            final int x, final int y, final int height, final int width) {
        if (color.equals("Red")) {
            if (type.equals("Half")) {
                Image img = super.resize(halfSimpleHeart, height, width);
                gc.drawImage(img, x, y);
            }
        }

        // TO-DO
    }
}
