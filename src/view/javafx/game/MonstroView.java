package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.SpritesExtractor;

/**
* View and animations of the Monstro enemy.
*/
public class MonstroView extends AbstractEntityView {
    private static List<Image> monstroSprite;
    private int monstroIndex;

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
     * Base constructor that extract the sprites from the sheet.
     * @throws IOException trying to get the resource image
     */
    public MonstroView() throws IOException {
        super();
        monstroIndex = 0;
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
        Image img = super.resize(monstroSprite.get(monstroIndex), height, width);
        gc.drawImage(img, x, y);
        monstroIndex = (monstroIndex + 1) % monstroSprite.size();
    }
}
