package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

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
            final BufferedImage image = ImageIO.read(
                    new File(HostHatView.class.getResource("/gameImgs/upgradesImgs/collectibles_375_hosthat.png").toURI()));
            img = SwingFXUtils.toFXImage(image, null);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    
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
