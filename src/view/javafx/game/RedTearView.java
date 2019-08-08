package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
 * View of the tears shot by the player.
 */
public class RedTearView extends AbstractEntityView {
    private static List<Image> enemyTear;

    static {
        BufferedImage img = null;
         try {
            img = ImageIO.read(BlueTearView.class.getResource("/gameImgs/tears.png"));
        } catch (IOException e) {
           e.printStackTrace();
        }
        final int delta = 32;
        final int tears = 13;
        final int cols = 8;

        enemyTear = (new SpritesExtractor(img, tears, 2, cols, delta, delta, 0, delta * 2)).extract();
        Collections.reverse(enemyTear);
    }
    private final List<Image> tears;
    private int index;

    /**
     * 
     * @param id 
     * @param gv The gameView to which this entityView is added
     */
    public RedTearView(final UUID id, final GameViewImpl gv) {
        super(id, gv);
        tears = RedTearView.enemyTear;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        gc.drawImage(this.tears.get(index), super.getX(), super.getY(), super.getHeight(), super.getWidth());
        index += 1;
        if (index > this.tears.size() && super.getGameView().isPresent()) {
            super.getGameView().get().removeEntity(this);
        }
    }
}
