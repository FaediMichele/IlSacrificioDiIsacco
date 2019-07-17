package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
* View of the hearts.
*/
public class HeartView {

    private final Image simpleHeart;
    private final Image halfSimpleHeart;
    private final Image blackHeart;
    private final Image halfBlackHeart;

    /**
     * Base constructor that extract the sprites from the sheet.
     * @throws IOException trying to get the resource image
     */
    public HeartView() throws IOException {
        final BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/pickup_001_heart.png"));
        final int delta = 30;
        this.simpleHeart = SwingFXUtils.toFXImage(img.getSubimage(0, 0, delta, delta), null);
        this.halfSimpleHeart = SwingFXUtils.toFXImage(img.getSubimage(delta + 2, 0, delta, delta), null);

        final int blackY = 64;
        this.blackHeart = SwingFXUtils.toFXImage(img.getSubimage(0, blackY, delta, delta), null);
        this.halfBlackHeart = SwingFXUtils.toFXImage(img.getSubimage(delta + 2, blackY, delta, delta), null);
    }
}
