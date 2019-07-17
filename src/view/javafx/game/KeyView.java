package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
* View of the key.
*/
public class KeyView {
    private final Image keySprite;

    /**
     * Base constructor that extract the sprite from the sheet.
     * @throws IOException trying to get the resource image
     */
    public KeyView() throws IOException {
        final BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/pickup_001_heart.png"));
        final int width = 16;
        final int height = 27;
        this.keySprite = SwingFXUtils.toFXImage(img.getSubimage(0, 0, width, height), null);
    }

}
