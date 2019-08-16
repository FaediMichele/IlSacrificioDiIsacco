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
 * View for the {@link EightInchNails}.
 *
 */
public class EightInchNailsView extends AbstractEntityView {
    private static Image img;

    static {
        try {
            final BufferedImage image = ImageIO.read(
                    new File(EightInchNailsView.class.getResource("/gameImgs/upgradesImgs/collectibles_359_8inchnails.png").toURI()));
            img = SwingFXUtils.toFXImage(image, null);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the image.
     */
    @Override
    public void draw(final GraphicsContext gc) {
        gc.drawImage(img, super.getX(), super.getY(), super.getWidth(), super.getHeight());
    }

}
