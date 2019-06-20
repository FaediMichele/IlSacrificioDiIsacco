package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
* View of the hearts.
*/
public class HeartView {

    private BufferedImage simpleHeart;
    private BufferedImage halfSimpleHeart;
    private BufferedImage blackHeart;
    private BufferedImage halfBlackHeart;

    /**
     * Base constructor that extract the sprites from the sheet.
     * @throws IOException trying to get the resource image
     */
    public HeartView() throws IOException {
        BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/pickup_001_heart.png"));
        final int delta = 30;
        simpleHeart = img.getSubimage(0, 0, delta, delta);
        halfSimpleHeart = img.getSubimage(delta + 2, 0, delta, delta);

        final int blackY = 64;
        blackHeart = img.getSubimage(0, blackY, delta, delta);
        halfSimpleHeart = img.getSubimage(delta + 2, blackY, delta, delta);
    }
}
