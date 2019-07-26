package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
* View of the Rocks.
*/
public class RockView extends AbstractEntityView {
    private static List<Image> rockSprites;

    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(RockView.class.getResource("/gameImgs/rocks_basement.png"));
            final int delta = 32;
            rockSprites = (new SpritesExtractor(img, 3, 1, 1, delta, delta)).extract();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Base constructor.
     */
    public RockView() {
        super();
    }

    /**
     * {@inheritDoc}
     *  This method also chooses the random rock to draw.
     */
    @Override
    public void draw(final GraphicsContext gc) {
        final int index = (new Random().nextInt(rockSprites.size()));
        final Image img = super.resize(rockSprites.get(index), super.getHeight(), super.getWidth());
        gc.drawImage(img, super.getX(), super.getY());
    }

}