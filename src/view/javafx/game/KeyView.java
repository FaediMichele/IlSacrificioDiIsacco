package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
* View of the key.
*/
public class KeyView extends AbstractEntityView {
    private static Image keySprite;

    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(KeyView.class.getResource("/gameImgs/pickup_003_key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        final int width = 16;
        final int height = 27;
        if (img != null) {
            keySprite = SwingFXUtils.toFXImage(img.getSubimage(0, 0, width, height), null);
        }
    }

    /**
     * 
     * @param id 
     */
    public KeyView(final UUID id) {
        super(id);
    }
    /**
     * @return the sprite of the key.
     */
    public static Image getKeySprite() {
        return keySprite;
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void draw(final GraphicsContext gc) {
        gc.drawImage(keySprite, super.getX(), super.getY(), super.getHeight(), super.getWidth());
    }
}
