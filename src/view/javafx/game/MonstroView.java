    package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
* View and animations of the Monstro enemy.
*/
public class MonstroView {
    private List<BufferedImage> faceSprite = new ArrayList<>();

    /**
     * Base constructor that extract the sprites from the sheet.
     * @throws IOException trying to get the resource image
     */
    public MonstroView() throws IOException {
        BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/boss_004_monstro.png"));
        final int deltaHeigth = 112;
        final int deltaWidth = 79;
        final int facesFirstLine = 5;

        for (int i = 0; i < facesFirstLine; i++) {
            faceSprite.add(img.getSubimage(i * deltaWidth + 1, 0, deltaWidth, deltaHeigth));
        }
        for (int i = 0; i < facesFirstLine - 1; i++) {
            faceSprite.add(img.getSubimage(i * deltaWidth + 1, deltaHeigth, deltaWidth, deltaHeigth));
        }
    }
}
