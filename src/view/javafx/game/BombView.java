package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    private static List<Image> explosionBombSprites; 

    private int triggeredIndex;
    private int explosionIndex;

    static {
        try {
            final int delta = 20;
            final int deltaY = 22;
            final int x = 6;
            bombSprite = (new SpritesExtractor(ImageIO.read(BombView.class.getResource("/gameImgs/bomba1.png")), 1, 1, 1, delta, deltaY, x, 4)).extract().get(0);
            triggeredBombSprite = new ArrayList<>();
            triggeredBombSprite.add(bombSprite);
            triggeredBombSprite.add(SwingFXUtils.toFXImage(ImageIO.read(BombView.class.getResource("/gameImgs/bomba2.png")), null));

            final BufferedImage img = ImageIO.read(BombView.class.getResource("/gameImgs/effect_029_explosion.png"));
            final int deltaExplosions = 96;
            final int explosions = 16;
            final int cols = 4;
            final LinkedList<Image> explosionSprites = new LinkedList<>((new SpritesExtractor(img, explosions, cols, cols, deltaExplosions, deltaExplosions)).extract());
            explosionBombSprites = explosionSprites.subList(0, explosions - 2);
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
     */
    public BombView(final GameView gameView) {
        super(gameView);
        this.triggeredIndex = 0;
        this.explosionIndex = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        if (super.getStatus().isPresent() && super.getStatus().get().equals(BasicStatusEnum.TRIGGERED)) {
            gc.drawImage(triggeredBombSprite.get(triggeredIndex), super.getX(), super.getY(), super.getHeight(), super.getWidth());
            triggeredIndex = (triggeredIndex + 1) % triggeredBombSprite.size();
        } else if (super.getStatus().isPresent() && super.getStatus().get().equals(BasicStatusEnum.EXPLODED)) {
            gc.drawImage(explosionBombSprites.get(explosionIndex), super.getX(), super.getY(), super.getHeight(), super.getWidth());
            explosionIndex += 1;
            if (explosionIndex >= explosionBombSprites.size()) {
                explosionIndex -= 10;
             }
        } else {
            gc.drawImage(bombSprite, super.getX(), super.getY(), super.getHeight(), super.getWidth());
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
