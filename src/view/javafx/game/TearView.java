package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.Pair;
import util.SpritesExtractor;

/**
* View and animations of the player and the enemies.
*/
public class TearView extends AbstractAnimatedEntityView {
    private static Pair<List<Image>, Integer> playerTear;
    private static Pair<List<Image>, Integer> enemyTear;

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

        playerTear.setX((new SpritesExtractor(img, tears, 2, cols, delta, delta)).extract());
        Collections.reverse(playerTear.getX());
        playerTear.setY(0);

        enemyTear.setX(new SpritesExtractor(img, tears, 2, cols, delta, delta, 0, 2 * delta).extract());
        Collections.reverse(enemyTear.getX());
        enemyTear.setY(0);
    }

    /**
     * Base constructor that extract the sprites from the sheet and fills the static lists (if they are not filled yet).
     * @throws IOException trying to get the resource image
     */
    public TearView() throws IOException {
        super();
    }

    /**
     * Draws the correct animation in the correct position of the canvas.
     * @param gc where to draw
     * @param entity entity that shooted the tera
     * @param x position on the x axis
     * @param y position on the y axis
     */
    public void draw(final GraphicsContext gc, final String entity, final int x, final int y) {
        if (entity.equals("Player")) {
            gc.drawImage(playerTear.getX().get(playerTear.getY()), x, y);
            playerTear.setY(playerTear.getY() + 1);
            if (playerTear.getY() > playerTear.getX().size()) {
                //dubbio 1: in questo caso il draw della lacrima non dovrà più essere chiamato perchè quando finisce le animazioni 
                //(che rendono la tear sempre più piccola) essa scompare...
            }
        }
        /*dubbio 2: - in questo caso devo ricevere in input non lo status, ma l'entità... uso la stringa?
         * */
    }
}
