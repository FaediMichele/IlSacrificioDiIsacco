package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    private Map<MovementEnum, List<Image>> gaperFaceSprites = new HashMap<>();

    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(GaperView.class.getResource("/gameImgs/monster_017_gaper.png"));
            final int delta = 32;
            movingDownFaceSprites = (new SpritesExtractor(img, 2, 1, 1, delta, delta)).extract();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Base constructor, initilizes the indexes.
     * 
     * @param id the {@link UUID}
     */
    public GaperView(final UUID id) {
        super(id);
        gaperFaceSprites.put(BasicMovementEnum.DOWN, movingDownFaceSprites);
        gaperFaceSprites.put(BasicMovementEnum.STATIONARY, movingDownFaceSprites.subList(0, 0));
        gaperFaceSprites.put(BasicMovementEnum.UP, IsaacView.getFaceSprites().get(BasicMovementEnum.UP));
        gaperFaceSprites.put(BasicMovementEnum.RIGHT, IsaacView.getFaceSprites().get(BasicMovementEnum.RIGHT));
        gaperFaceSprites.put(BasicMovementEnum.DOWN, IsaacView.getFaceSprites().get(BasicMovementEnum.DOWN));
    }

    /**
     * Default animation for {@link IsaacView}.
     */
    @Override
    public void def(final MovementEnum move) {
        super.setSprites(move, gaperFaceSprites);
    }
}
