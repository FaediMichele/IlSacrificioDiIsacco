package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import model.enumeration.BasicMovementEnum;
import model.enumeration.MovementEnum;
import util.SpritesExtractor;

/**
 * View and animations of the Gaper enemy.
 */
public class GaperView extends IsaacView {

    private static List<Image> movingDownFaceSprites;
    private final Map<MovementEnum, List<Image>> gaperFaceSprites = new HashMap<>();
    private final Map<MovementEnum, Integer> gaperFaceIndex = new HashMap<>();


    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(GaperView.class.getResourceAsStream("/gameImgs/monster_017_gaper.png"));
            final int delta = 32;
            movingDownFaceSprites = (new SpritesExtractor(img, 2, 1, 1, delta, delta)).extract();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Base constructor, initilizes the indexes.
     * @param gameView is to which the entity belongs.
     */
    public GaperView(final GameView gameView) {
        super(gameView);
        gaperFaceSprites.put(BasicMovementEnum.DOWN, movingDownFaceSprites);
        gaperFaceSprites.put(BasicMovementEnum.STATIONARY, movingDownFaceSprites);
        gaperFaceSprites.put(BasicMovementEnum.UP, super.getFaceSprites().get(BasicMovementEnum.UP));
        gaperFaceSprites.put(BasicMovementEnum.RIGHT, super.getFaceSprites().get(BasicMovementEnum.RIGHT));
        gaperFaceSprites.put(BasicMovementEnum.LEFT, super.getFaceSprites().get(BasicMovementEnum.LEFT));

        gaperFaceIndex.put(BasicMovementEnum.UP, 0);
        gaperFaceIndex.put(BasicMovementEnum.DOWN, 0);
        gaperFaceIndex.put(BasicMovementEnum.RIGHT, 0);
        gaperFaceIndex.put(BasicMovementEnum.LEFT, 0);
        gaperFaceIndex.put(BasicMovementEnum.STATIONARY, 0);
    }

    /**
     * Default animation for {@link AbstractPlayerView}.
     */
    @Override
    public void def(final MovementEnum move) {
        if (super.setSprites(move, gaperFaceSprites, gaperFaceIndex) && !move.equals(BasicMovementEnum.STATIONARY)) {
            this.gaperFaceIndex.put(move, 1);
        } else {
            this.gaperFaceIndex.put(move, 0);
        }
    }

    /**
     * When the entity dies.
     * @param move the last movement
     */
    @Override
    public void dead(final MovementEnum move) {
        super.disappear(move);
    }
}
