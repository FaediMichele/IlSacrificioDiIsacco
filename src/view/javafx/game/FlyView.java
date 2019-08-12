package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.enumeration.BasicStatusEnum;
import model.enumeration.MovementEnum;
import util.SpritesExtractor;

/**
 * View and animations of the Fly enemy.
 */
public class FlyView extends AbstractEntityView {

    private static List<Image> flySprite;
    private static List<Image> explodingFlySprite;

    static {
        try {
            final BufferedImage img = ImageIO.read(FlyView.class.getResource("/gameImgs/monster_010_fly_hush_2.png"));
            final int deltaFly = 32;
            final int explodingSpritesNumber = 11;
            flySprite = (new SpritesExtractor(img, 2, 1, 1, deltaFly, deltaFly)).extract();
            explodingFlySprite = (new SpritesExtractor(img, explodingSpritesNumber, 3, 3, deltaFly * 2, deltaFly * 2, 0, deltaFly * 2)).extract();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int index;
    private int explodingIndex;

    /**
     * Create a new FlyView.
     */
    public FlyView() {
        super();
        index = 0;
        explodingIndex = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        if (super.getStatus().isPresent() && super.getStatus().get().equals(BasicStatusEnum.EXPLODED)) {
            gc.drawImage(explodingFlySprite.get(explodingIndex), super.getX(), super.getY(), super.getHeight(), super.getWidth());
            explodingIndex += 1;
            if (explodingIndex > explodingFlySprite.size() && super.getGameView().isPresent()) {
                super.getGameView().get().removeEntity(this);
            }
        } else {
            gc.drawImage(flySprite.get(index), super.getX(), super.getY(), super.getHeight(), super.getWidth());
            index = (index + 1) % flySprite.size();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exploded(final MovementEnum move) {
        super.setStatus(BasicStatusEnum.EXPLODED);
    }
}
