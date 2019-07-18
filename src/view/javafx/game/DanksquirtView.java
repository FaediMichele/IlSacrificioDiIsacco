package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import util.SpritesExtractor;

/**
* View and animations of the Danksquirt enemy.
*/
public class DanksquirtView extends AbstractEntityView {
    private final List<Image> danksquirtSprite;

    /**
     * Base constructor that extract the sprites from the sheet.
     * @throws IOException trying to get the resource image
     */
    public DanksquirtView() throws IOException {
        super();
        final BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/220.001_danksquirt.png"));
        final int delta = 64;
        final int danksquirts = 5;
        this.danksquirtSprite = (new SpritesExtractor(img, danksquirts, 2, 3, delta, delta)).extract();
    }
}
