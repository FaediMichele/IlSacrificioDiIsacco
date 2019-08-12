package view.javafx.game;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
* View of the hearts.
*/
/**
* View of the hearts.
*/
public class BlackHeartView extends AbstractHeartView {

    private static Image blackHeart;
    private static Image halfBlackHeart;

    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(BlackHeartView.class.getResource("/gameImgs/pickup_001_heart.png"));
            final int delta = 30;
            final int blackY = 64;
            blackHeart = SwingFXUtils.toFXImage(img.getSubimage(0, blackY, delta, delta), null);
            halfBlackHeart = SwingFXUtils.toFXImage(img.getSubimage(delta + 2, blackY, delta, delta), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Sets the black hearts as the ones to draw.
     */
    public BlackHeartView() {
        super(blackHeart, halfBlackHeart);
    }
}
