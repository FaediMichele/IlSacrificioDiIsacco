package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
* View of the Rocks.
*/
public class RockView extends AbstractEntityView {
    private static List<Image> rockSprites;
    private final int index;
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
     * @param id 
     */
    public RockView(final UUID id) {
        super(id);
        this.index = (new Random().nextInt(rockSprites.size()));
    }

    /**
     * {@inheritDoc}
     *  This method also chooses the random rock to draw.
     */
    @Override
    public void draw(final GraphicsContext gc) {
        gc.drawImage(rockSprites.get(index), super.getX(), super.getY(), super.getHeight(), super.getWidth());
    }

}
