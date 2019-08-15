package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
* View and animations of the Monstro enemy.
*/
public class MonstroView extends AbstractEntityView {
    private static List<Image> monstroSprite = new ArrayList<Image>();
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
        final List<Image> monstroSpriteTmp = (new SpritesExtractor(img, monstros, 2, cols, width, height)).extract();
        monstroSpriteTmp.forEach(m -> {
            monstroSprite.add(m);
            monstroSprite.add(m);
        });
    }

    /**
     * Create a new MontroView.
     */
    public MonstroView() {
        super();
        index = 0;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        gc.drawImage(monstroSprite.get(index), super.getX(), super.getY(), super.getHeight(), super.getWidth());
        index = (index + 1) % monstroSprite.size();
    }
}
