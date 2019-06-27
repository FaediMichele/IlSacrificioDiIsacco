package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import util.SpritesExtractor;

/**
* View and animations of the Gaper enemy.
*/
public class GaperView extends IsaacView {
    /**
     * Base constructor that extract the sprites from the sheet.
     * @throws IOException trying to get the resource image
     */
    public GaperView() throws IOException {
        super();
    }

    /**
     * Changes some isaac faces to let it become the Gaper.
     * @throws IOException trying to read the image
     */
    protected final void extractGaperFaces() throws IOException {
        BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/monster_017_gaper.png"));
        final int delta = 32;
        super.setMovingDownFaceSprite(super.toFXImageList((new SpritesExtractor(img, 2, 1, 1, delta, delta)).extract()));
    }
}
