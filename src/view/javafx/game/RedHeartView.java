package view.javafx.game;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
* View of the hearts.
*/
public class RedHeartView extends AbstractHeartView {

    private static Image simpleHeart;
    private static Image halfSimpleHeart;

    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(BlackHeartView.class.getResource("/gameImgs/pickup_001_heart.png"));
            final int delta = 30;
            simpleHeart = SwingFXUtils.toFXImage(img.getSubimage(0, 0, delta, delta), null);
            halfSimpleHeart = SwingFXUtils.toFXImage(img.getSubimage(delta + 2, 0, delta, delta), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /**
     * Sets the red hearts as the ones to draw.
     * @param gameView is to which the entity belongs.
     */
    public RedHeartView(final GameView gameView) {
        super(simpleHeart, halfSimpleHeart, gameView);
    }

}
