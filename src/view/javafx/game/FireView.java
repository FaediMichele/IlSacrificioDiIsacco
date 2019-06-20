package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import util.SpritesExtractor;

/**
* View and animations of the fire.
*/
public class FireView {
    private List<BufferedImage> fire;
    private List<BufferedImage> dyingFire;
    private List<BufferedImage> fireGrid;

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
        fire = (new SpritesExtractor(fireSheet, fires, 1, fires, delta, delta)).extract();
        dyingFire = (new SpritesExtractor(fireSheet, fires + 1, 1, fires + 1, delta, delta, 0, delta)).extract();

        BufferedImage fireGridSheet = ImageIO.read(getClass().getResource(fireGridPath));
        final int deltaGrid = 32;
        final int grids = 8;
        fireGrid = (new SpritesExtractor(fireGridSheet, grids, 2, 4, deltaGrid, deltaGrid)).extract();
    }
}
