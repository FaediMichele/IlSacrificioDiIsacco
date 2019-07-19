package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
* View and animations of the Danksquirt enemy.
*/
public class DanksquirtView extends AbstractEntityView {
    private static List<Image> danksquirtSprite;
    private int index;

    static {
        BufferedImage img;
        try {
            img = ImageIO.read(DanksquirtView.class.getResource("/gameImgs/220.001_danksquirt.png"));
            final int delta = 64;
            final int danksquirts = 5;
            danksquirtSprite = (new SpritesExtractor(img, danksquirts, 2, 3, delta, delta)).extract();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Base constructor, initilizes the index.
     */
    public DanksquirtView() {
        super();
        this.index = 0;
    }

    /**
     * Draws the correct animation in the correct position of the canvas.
     * @param gc where to draw
     * @param x position on the x axis
     * @param y position on the y axis
     * @param height of a sprite
     * @param width of a sprite
     */
    public void draw(final GraphicsContext gc, final int x, final int y, final int height, final int width) {
        Image img = super.resize(danksquirtSprite.get(index), height, width);
        gc.drawImage(img, x, y);
        index = (index + 1) % danksquirtSprite.size();
    }
}
