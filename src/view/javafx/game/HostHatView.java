package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * View for the {@link HostHat}.
 *
 */
public class HostHatView extends AbstractEntityView {

    private static Image img;

    static {
        try {
            final BufferedImage image = ImageIO.read(HostHatView.class.getResourceAsStream("/gameImgs/upgradesImgs/collectibles_375_hosthat.png"));
            img = SwingFXUtils.toFXImage(image, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 
     * @param gameView is to which the entity belongs.
     */
    public HostHatView(final GameView gameView) {
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
