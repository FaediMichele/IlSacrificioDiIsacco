package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.enumeration.BasicStatusEnum;
import model.enumeration.MovementEnum;
import util.SpritesExtractor;

/**
 * View and animations of the Bomb.
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
     * @return the sprite of the bomb.
     */
    public static Image getBombSprite() {
        return bombSprite;
    }

    /**
     * Base constructor, initilizes the indexes.
     * @param id 
     * @param gv The gameView to which this entityView is added
     */
    public BombView(final UUID id, final GameViewImpl gv) {
        super(id, gv);
        this.triggeredIndex = 0;
        this.explosionIndex = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        if (super.getStatus().isPresent() && super.getStatus().get().equals(BasicStatusEnum.TRIGGERED)) {
            final Image img = super.resize(triggeredBombSprite.get(triggeredIndex), super.getHeight(), super.getWidth());
            gc.drawImage(img, super.getX(), super.getY());
            triggeredIndex = (triggeredIndex + 1) % triggeredBombSprite.size();
        } else if (super.getStatus().isPresent() && super.getStatus().get().equals(BasicStatusEnum.EXPLODED)) {
            final Image img = super.resize(explosionBombSprite.get(explosionIndex), super.getHeight(), super.getWidth());
            gc.drawImage(img, super.getX(), super.getY());
            explosionIndex += 1;
            if (explosionIndex > explosionBombSprite.size() && super.getGameView().isPresent()) {
                super.getGameView().get().removeEntity(this);
            }
        } else {
            final Image img = super.resize(bombSprite, super.getHeight(), super.getWidth());
            gc.drawImage(img, super.getX(), super.getY());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exploded(final MovementEnum move) {
        super.setStatus(BasicStatusEnum.EXPLODED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggered(final MovementEnum move) {
        super.setStatus(BasicStatusEnum.TRIGGERED);
    }
}
