package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
    private int playerIndex;
    private int enemyIndex;

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
     * @param gv The gameView to which this entityView is added
     */
    public TearView(final GameView gv) {
        super(gv);
        this.playerIndex = 0;
        this.enemyIndex = 0;
    }

    /**
     * Draws the correct animation in the correct position of the canvas.
     * @param gc where to draw
     * @param entity entity that shooted the tear
     * @param x position on the x axis
     * @param y position on the y axis
     * @param height of a sprite
     * @param width of a sprite
     */
    public void draw(final GraphicsContext gc, final Class<? extends AbstractEntityView> entity, final int x, final int y, final int height, final int width) {
        if (entity.equals(IsaacView.class)) {
            Image img = super.resize(playerTear.get(playerIndex), height, width);
            gc.drawImage(img, x, y);
            playerIndex += 1;
            if (playerIndex > playerTear.size() && super.getGameView().isPresent()) {
                super.getGameView().get().removeEntity(this);
            }
        }

        if (entity.equals(MonstroView.class) || entity.equals(GaperView.class) || entity.equals(DanksquirtView.class)) {
            Image img = super.resize(enemyTear.get(enemyIndex), height, width);
            gc.drawImage(img, x, y);
            enemyIndex += 1;
            if (enemyIndex > enemyTear.size() && super.getGameView().isPresent()) {
                super.getGameView().get().removeEntity(this);
            }
        }
    }
}
