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
* View and animations of the player and the enemies.
*/
public class TearView extends AbstractEntityView {
    private static List<Image> playerTear;
    private static List<Image> enemyTear;

    private final List<Image> tears;
    private int index;

    static {
        BufferedImage img = null;
         try {
            img = ImageIO.read(TearView.class.getResource("/gameImgs/tears.png"));
        } catch (IOException e) {
           e.printStackTrace();
        }
        final int delta = 32;
        final int tears = 13;
        final int cols = 8;

        playerTear = (new SpritesExtractor(img, tears, 2, cols, delta, delta)).extract();
        Collections.reverse(playerTear);

        enemyTear = (new SpritesExtractor(img, tears, 2, cols, delta, delta, 0, 2 * delta).extract());
        Collections.reverse(enemyTear);
    }

    /**
     * Base constructor, initilizes the indexes.
     * @param id 
     * @param gv The gameView to which this entityView is added
     * @param entityClass the class determines which list of tears is needed
     */
    public TearView(final UUID id, final GameViewImpl gv, final Class<? extends AbstractEntityView> entityClass) {
        super(id, gv);
        this.index = 0;
        this.index = 0;

        if (entityClass.equals(IsaacView.class)) {
            tears = playerTear;
        } else if (entityClass.equals(MonstroView.class) || entityClass.equals(GaperView.class) || entityClass.equals(DanksquirtView.class)) {
            tears = enemyTear;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        final Image img = super.resize(this.tears.get(index), super.getHeight(), super.getWidth());
        gc.drawImage(img, super.getX(), super.getY());
        index += 1;
        if (index > this.tears.size() && super.getGameView().isPresent()) {
            super.getGameView().get().removeEntity(this);
        }
    }
}
