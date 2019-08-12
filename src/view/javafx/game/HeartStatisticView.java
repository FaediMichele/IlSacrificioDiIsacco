package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.enumeration.BasicHeartEnum;
import model.enumeration.HeartEnum;
import util.Pair;

/**
 * Class to draw the statistics of the hearts.
 */
public class HeartStatisticView extends AbstractStatisticView {

    private static Image simpleHeart;
    private static Image halfSimpleHeart;
    private static Image blackHeart;
    private static Image halfBlackHeart;

    static {
        BufferedImage img = null;
        try {
            img = ImageIO.read(RedHeartView.class.getResource("/gameImgs/ui_hearts.png"));
            final int delta = 16;
            simpleHeart = SwingFXUtils.toFXImage(img.getSubimage(0, 0, delta, delta), null);
            halfSimpleHeart = SwingFXUtils.toFXImage(img.getSubimage(delta, 0, delta, delta), null);
            blackHeart = SwingFXUtils.toFXImage(img.getSubimage(delta * 2, delta, delta, delta), null);
            halfBlackHeart = SwingFXUtils.toFXImage(img.getSubimage(delta * 3, delta, delta, delta), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final List<Image> heartsToDraw = new LinkedList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        final int y = super.getIndex() * super.getDelta() + super.getMargin();
        for (int i = 0; i < heartsToDraw.size(); i++) {
            gc.drawImage(heartsToDraw.get(i), super.getDelta() * i + super.getMargin(), y, super.getDelta(), super.getDelta());
        }
        heartsToDraw.clear();
    }

    /**
     * As a classic statistic view sets the Number of the items,
     * the hearts needs to know the color and the value of each item.
     * @param hearts list of colors and values of the hearts
     */
    public void setHearts(final List<Pair<HeartEnum, Double>> hearts) {
        // if (hearts.stream().filter(h -> h.getX().equals(BasicHeartEnum.BLACK)).findAny().isPresent()) {
        //}
        hearts.forEach(h -> {
            if (h.getX().equals(BasicHeartEnum.RED)) {
                if (h.getY() <= 1.0 && h.getY() > 0.5) {
                    heartsToDraw.add(simpleHeart);
                } else if (h.getY() <= 0.5 && h.getY() > 0.0) {
                    heartsToDraw.add(halfSimpleHeart);
                } else {
                    throw new IllegalArgumentException();
                }
            } else if (h.getX().equals(BasicHeartEnum.BLACK)) {
                if (h.getY() <= 1.0 && h.getY() > 0.5) {
                    heartsToDraw.add(blackHeart);
                } else if (h.getY() <= 0.5 && h.getY() > 0.0) {
                    heartsToDraw.add(halfBlackHeart);
                } else {
                    throw new IllegalArgumentException();
                }
            }
        });
    }
}
