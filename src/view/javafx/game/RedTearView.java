package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
 * View of the tears shot by the player.
 */
public class RedTearView extends AbstractTearView {
    private static List<Image> enemyTear;

    static {
        BufferedImage img = null;
         try {
            img = ImageIO.read(BlueTearView.class.getResource("/gameImgs/tears.png"));
        } catch (IOException e) {
           e.printStackTrace();
        }
        final int delta = 32;
        final int tears = 13;
        final int cols = 8;

        enemyTear = (new SpritesExtractor(img, tears, 2, cols, delta, delta, 0, delta * 2)).extract();
        Collections.reverse(enemyTear);
        enemyTear.forEach(t -> {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(t, null), "png", new File(enemyTear.indexOf(t)+".png"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    /**
     * 
     */
    public RedTearView() {
        super(enemyTear);
    }
}
