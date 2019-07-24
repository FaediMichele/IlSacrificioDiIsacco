package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class used to draw the statistics of the hearts.
 */
public class HeartStatisticView extends AbstractStatisticView {

    private static Image simpleHeart;
    private static Image halfSimpleHeart;
    private static Image blackHeart;
    private static Image halfBlackHeart;

    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(HeartView.class.getResource("/gameImgs/ui_hearts.png"));
            final int delta = 16;
            simpleHeart = SwingFXUtils.toFXImage(img.getSubimage(0, 0, delta, delta), null);
            halfSimpleHeart = SwingFXUtils.toFXImage(img.getSubimage(delta, 0, delta, delta), null);
            blackHeart = SwingFXUtils.toFXImage(img.getSubimage(delta * 2, delta, delta, delta), null);
            halfBlackHeart = SwingFXUtils.toFXImage(img.getSubimage(delta * 3, delta, delta, delta), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Image heart;
    private final Image halfHeart;

    /**
     * @param colour the colour of this heart
     */
    protected HeartStatisticView(final HeartColour colour) {
        super();

        if (colour.equals(HeartColour.RED)) {
            this.heart = super.resize(simpleHeart);
            this.halfHeart = super.resize(halfSimpleHeart);
        } else {
            this.heart = super.resize(blackHeart);
            this.halfHeart = super.resize(halfBlackHeart);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        final int y = super.getIndex() * super.getDelta() + super.getMargin();
        int i = 0;
        for (; i < Math.floor(super.getNumber()); i++) {
            gc.drawImage(heart, super.getDelta() * i + super.getMargin(), y);
        }

        if ((super.getNumber() - Math.floor(super.getNumber())) >= 0.5) {
            i++;
            gc.drawImage(halfHeart, super.getDelta() * i + super.getMargin(), y);
        }
    }

}
