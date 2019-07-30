package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.sun.javafx.scene.traversal.Direction;

import javafx.scene.image.Image;
import util.SpritesExtractor;
import util.enumeration.MovementEnum;

/**
* View and animations of the Gaper enemy.
*/
public class GaperView extends IsaacView {

    private static Map<Direction, List<Image>> faceSprites = new HashMap<>();

    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(GaperView.class.getResource("/gameImgs/monster_017_gaper.png"));
            final int delta = 32;
            final List<Image> gaperMovingDownFaceSprite = (new SpritesExtractor(img, 2, 1, 1, delta, delta)).extract();
            faceSprites = IsaacView.getStaticFaceSprites();
            faceSprites.put(Direction.DOWN, gaperMovingDownFaceSprite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param id 
     */
    public GaperView(final UUID id) {
        super(id);
    }
    /**
     * @return the faceSprites
     */
    public Map<Direction, List<Image>> getFaceSprites() {
        return GaperView.faceSprites;
    }

    @Override
    public final void dead(final MovementEnum move) {
        System.out.println("DEAD!");
    }
}
