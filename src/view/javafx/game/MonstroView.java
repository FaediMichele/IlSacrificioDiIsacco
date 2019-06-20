package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
* View and animations of the Monstro enemy.
*/
public class MonstroView extends AbstractAnimatedEntityView {
    private List<Image> monstroSprite;

    /**
     * Base constructor that extract the sprites from the sheet.
     * @throws IOException trying to get the resource image
     */
    public MonstroView() throws IOException {
        BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/boss_004_monstro.png"));
        final int height = 112;
        final int width = 79;
        final int monstros = 9;
        final int cols = 5;
        monstroSprite = super.toFXImageList((new SpritesExtractor(img, monstros, 2, cols, width, height)).extract());

        super.setEntityActualSprites(monstroSprite);
        super.animate();
    }
}
