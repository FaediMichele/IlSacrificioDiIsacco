package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
 * View and animations of the Bomb enemy.
 */

public class BombView extends AbstractEntityView {
    private static Image bombSprite;
    private static List<Image> triggeredBombSprite;
    private static List<Image> explosionBombSprite;

    private int triggeredIndex;
    private int explosionIndex;

    static {
        try {
            bombSprite = SwingFXUtils.toFXImage(ImageIO.read(BombView.class.getResource("/gameImgs/bomba1.png")), null);
            triggeredBombSprite = new ArrayList<>();
            triggeredBombSprite.add(bombSprite);
            triggeredBombSprite.add(SwingFXUtils.toFXImage(ImageIO.read(BombView.class.getResource("/gameImgs/bomba2.png")), null));

            final BufferedImage img = ImageIO.read(BombView.class.getResource("/gameImgs/effect_029_explosion.png"));
            final int delta = 96;
            final int explosions = 16;
            final int cols = 4;
            explosionBombSprite = (new SpritesExtractor(img, explosions, cols, cols, delta, delta)).extract();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Base constructor, initilizes the indexes.
     * @param gv The gameView to which this entityView is added
     */
    public BombView(final GameViewImpl gv) {
        super(gv);
        this.triggeredIndex = 0;
        this.explosionIndex = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        if (super.getStatus().equals("collectible")) {
            final Image img = super.resize(bombSprite, super.getHeight(), super.getWidth());
            gc.drawImage(img, super.getX(), super.getY());
        }

        if (super.getStatus().equals("triggered")) {
            final Image img = super.resize(triggeredBombSprite.get(triggeredIndex), super.getHeight(), super.getWidth());
            gc.drawImage(img, super.getX(), super.getY());
            triggeredIndex = (triggeredIndex + 1) % triggeredBombSprite.size();
        }

        if (super.getStatus().equals("explosion")) {
            final Image img = super.resize(explosionBombSprite.get(explosionIndex), super.getHeight(), super.getWidth());
            gc.drawImage(img, super.getX(), super.getY());
            explosionIndex += 1;
            if (explosionIndex > explosionBombSprite.size() && super.getGameView().isPresent()) {
                super.getGameView().get().removeEntity(this);
            }
        }
    }
}
