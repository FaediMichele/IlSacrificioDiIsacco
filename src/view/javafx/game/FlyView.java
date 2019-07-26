package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
     * Base constructor, initilizes the indexes.
     * @param gv The gameView to which this entityView is added
     */
    public FlyView(final GameViewImpl gv) {
        super(gv);
        this.explodingIndex = 0;
        this.index = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        if (super.getStatus().isPresent() && super.getStatus().get().equals("explosion")) {
            final Image img = super.resize(explodingFlySprite.get(explodingIndex), super.getHeight(), super.getWidth());
            gc.drawImage(img, super.getX(), super.getY());
            explodingIndex += 1;
            if (explodingIndex > explodingFlySprite.size() && super.getGameView().isPresent()) {
                super.getGameView().get().removeEntity(this);
            }
        } else {
            final Image img = super.resize(flySprite.get(index), super.getHeight(), super.getWidth());
            gc.drawImage(img, super.getX(), super.getY());
            index = (index + 1) % flySprite.size();
        }
    }
}
