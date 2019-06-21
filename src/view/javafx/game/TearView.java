package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
* View and animations of the player and the enemies.
*/
public class TearView extends AbstractAnimatedEntityView {
    private List<Image> playerTear;
    private List<Image> enemyTear;


    /**
     * Base constructor that extract the sprites from the sheet.
     * @throws IOException trying to get the resource image
     */
    public TearView() throws IOException {
        BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/tears.png"));
        final int delta = 32;
        final int tears = 13;
        final int cols = 8;

        playerTear = super.toFXImageList((new SpritesExtractor(img, tears, 2, cols, delta, delta)).extract());
        Collections.reverse(playerTear);

        enemyTear = super.toFXImageList((new SpritesExtractor(img, tears, 2, cols, delta, delta, 0, 2 * delta)).extract());
        Collections.reverse(enemyTear);
    }
}
