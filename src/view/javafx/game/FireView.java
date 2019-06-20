package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
* View and animations of the fire. 
* With this class you can create a red, blue or purple fire based on the path of which sheet you pass in the constructor.
*/
public class FireView extends AbstractAnimatedEntityView {
    private final List<Image> fireSprite;
    private final List<Image> dyingFireSprite;
    private final List<Image> fireGridSprite;

    /**
     * Base constructor that extract the sprites from the sheet.
     * @throws IOException trying to get the resource image
     * @param firePath path of the sheet with the type of fire we want to extract (red, blue or purple)
     * @param fireGridPath same as firePath
     */
    public FireView(final String firePath, final String fireGridPath) throws IOException {
        BufferedImage fireSheet = ImageIO.read(getClass().getResource(firePath));
        final int delta = 48;
        final int fires = 6;
        fireSprite = super.toFXImageList((new SpritesExtractor(fireSheet, fires, 1, fires, delta, delta)).extract());
        dyingFireSprite = super.toFXImageList((new SpritesExtractor(fireSheet, fires + 1, 1, fires + 1, delta, delta, 0, delta)).extract());

        BufferedImage fireGridSheet = ImageIO.read(getClass().getResource(fireGridPath));
        final int deltaGrid = 32;
        final int grids = 8;
        fireGridSprite = super.toFXImageList((new SpritesExtractor(fireGridSheet, grids, 2, 4, deltaGrid, deltaGrid)).extract());

        /*super.setEntityActualSprites(fireSprite, fireGridSprite);
        super.animate();*/
    }
}
