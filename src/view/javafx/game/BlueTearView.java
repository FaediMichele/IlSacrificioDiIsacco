package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
 * View of the tears shot by the player.
 */
public class BlueTearView extends AbstractTearView {
    private static List<Image> playerTear;

    static {
        BufferedImage img = null;
         try {
            img = ImageIO.read(BlueTearView.class.getResourceAsStream("/gameImgs/tears.png"));
        } catch (IOException e) {
           e.printStackTrace();
        }
        final int delta = 32;
        final int tears = 13;
        final int cols = 8;

        playerTear = (new SpritesExtractor(img, tears, 2, cols, delta, delta)).extract();
        Collections.reverse(playerTear);
    }

    /**
     * Sets the tears to be blue.
     * @param gameView is to which the entity belongs.
     */
    public BlueTearView(final GameView gameView) {
        super(playerTear, gameView);
    }

}
