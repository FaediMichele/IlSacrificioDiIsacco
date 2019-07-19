package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
* View of the hearts.
*/
public class HeartView extends AbstractStatusEntityView {

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

    private final Image heart;
    private final Image halfHeart;

    /**
     * Base constructor, initilizes the indexes and sets the images to used based on the colour of the heart.
     * @param colour the colour of this heart
     */
    public HeartView(final HeartColour colour) {
        super();

        if (colour.equals(HeartColour.RED)) {
            this.heart = simpleHeart;
            this.halfHeart = halfSimpleHeart;
        } else {
            this.heart = blackHeart;
            this.halfHeart = halfBlackHeart;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void draw(final GraphicsContext gc, final String status, final int x, final int y, final int height, final int width) {
        if (status.equals("full")) {
            Image img = super.resize(heart, height, width);
            gc.drawImage(img, x, y);
        }

        if (status.equals("half")) {
            Image img = super.resize(halfHeart, height, width);
            gc.drawImage(img, x, y);
        }
    }
}
