package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
* View and animations of the Monstro enemy.
*/
public class MonstroView extends AbstractEntityView {
    private static List<Image> monstroSprite;
    private int index;

    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(MonstroView.class.getResource("/gameImgs/boss_004_monstro.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        final int height = 112;
        final int width = 79;
        final int monstros = 9;
        final int cols = 5;
        monstroSprite = (new SpritesExtractor(img, monstros, 2, cols, width, height)).extract();
    }

    /**
     * Base constructor, initilizes the index.
     * @param id 
     */
    public MonstroView(final UUID id) {
        super(id);
        index = 0;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        final Image img = super.resize(monstroSprite.get(index), super.getHeight(), super.getWidth());
        gc.drawImage(img, super.getX(), super.getY());
        index = (index + 1) % monstroSprite.size();
    }
}
