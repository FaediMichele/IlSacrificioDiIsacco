package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
 * View and animations of the Bomb enemy.
 */

public class BombView extends AbstractEntityView {
    private final Image bombSprite;
    private final List<Image> triggeredBombSprite;
    private final List<Image> explosionBombSprite;

    /**
     * Base constructor that extract the sprites from the sheet.
     * @throws IOException trying to get the resource image
     */
    public BombView() throws IOException {
        super();
        this.bombSprite = SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/gameImgs/bomba1.png")), null);
        this.triggeredBombSprite = new ArrayList<>();
        this.triggeredBombSprite.add(bombSprite);
        this.triggeredBombSprite.add(SwingFXUtils.toFXImage(ImageIO.read(getClass().getResource("/gameImgs/bomba2.png")), null));

        final BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/effect_029_explosion.png"));
        final int delta = 96;
        final int explosions = 16;
        final int cols = 4;
        this.explosionBombSprite = (new SpritesExtractor(img, explosions, cols, cols, delta, delta)).extract();
    }
}
