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
public class BlueTearView extends AbstractEntityView {
    private static List<Image> playerTear;

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

        playerTear = (new SpritesExtractor(img, tears, 2, cols, delta, delta)).extract();
        Collections.reverse(playerTear);
    }
    private final List<Image> tears;
    private int index;

    /**
     * 
     * @param id 
     */
    public BlueTearView(final UUID id) {
        super(id);
        tears = BlueTearView.playerTear;
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
