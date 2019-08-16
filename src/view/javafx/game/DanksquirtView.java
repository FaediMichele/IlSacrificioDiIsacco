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

    /**
     * Create a new DanksquirtView.
     * @param gameView is to which the entity belongs.
     */
    public DanksquirtView(final GameView gameView) {
        super(gameView);
        index = 0;
    }

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
     * 
     * {@inheritDoc}
     */
    public void draw(final GraphicsContext gc) {
        gc.drawImage(danksquirtSprite.get(index), super.getX(), super.getY(), super.getHeight(), super.getWidth());
        index = (index + 1) % danksquirtSprite.size();
    }
}
