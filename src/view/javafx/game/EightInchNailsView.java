package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * View for the {@link EightInchNails}.
 *
 */
public class EightInchNailsView extends AbstractEntityView {

    private static Image img;

    static {
        try {
            final BufferedImage image = ImageIO.read(EightInchNailsView.class.getResourceAsStream("/gameImgs/upgradesImgs/collectibles_359_8inchnails.png"));
            img = SwingFXUtils.toFXImage(image, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param gameView is to which the entity belongs.
     */
    public EightInchNailsView(final GameView gameView) {
        super(gameView);
    }
    /**
     * Draws the image.
     */
    @Override
    public void draw(final GraphicsContext gc) {
        gc.drawImage(img, super.getX(), super.getY(), super.getWidth(), super.getHeight());
    }

}
