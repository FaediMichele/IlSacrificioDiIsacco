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

    @Override
    protected final void extractFaceSprite() throws IOException {
        BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/monster_017_gaper.png"));
        final int delta = 32;
        super.setFaceSprite(super.toFXImageList((new SpritesExtractor(img, 2, 1, 1, delta, delta)).extract()));
    }
}
