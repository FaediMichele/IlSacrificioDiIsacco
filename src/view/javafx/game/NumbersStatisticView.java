package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 */
public class NumbersStatisticView extends AbstractStatisticView {
    private static final Map<Integer, Image> NUMBER_SPRITES = new HashMap<>();

    static {
        BufferedImage img;
        try {
            img = ImageIO.read(MonstroView.class.getResource("/gameImgs/font.png"));
            NumbersStatisticView.initNumbers(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<Image> img = Optional.empty();
    private final Map<Integer, Image> numbers = new HashMap<>();

    @SuppressWarnings("all")
    private static void initNumbers(final BufferedImage img) {
        int i = 0;
        final int height = 12;
        final int width = 8;
        NUMBER_SPRITES.put(i++, SwingFXUtils.toFXImage(img.getSubimage(237, 57, width, height), null));
        NUMBER_SPRITES.put(i++, SwingFXUtils.toFXImage(img.getSubimage(31, 73, width, height), null));
        NUMBER_SPRITES.put(i++, SwingFXUtils.toFXImage(img.getSubimage(22, 60, width, height), null));
        NUMBER_SPRITES.put(i++, SwingFXUtils.toFXImage(img.getSubimage(102, 58, width, height), null));
        NUMBER_SPRITES.put(i++, SwingFXUtils.toFXImage(img.getSubimage(32, 60, width + 1, height), null));
        NUMBER_SPRITES.put(i++, SwingFXUtils.toFXImage(img.getSubimage(111, 58, width, height), null));
        NUMBER_SPRITES.put(i++, SwingFXUtils.toFXImage(img.getSubimage(129, 58, width, height), null));
        NUMBER_SPRITES.put(i++, SwingFXUtils.toFXImage(img.getSubimage(210, 57, width, height), null));
        NUMBER_SPRITES.put(i++, SwingFXUtils.toFXImage(img.getSubimage(138, 58, width, height), null));
        NUMBER_SPRITES.put(i++, SwingFXUtils.toFXImage(img.getSubimage(147, 58, width, height), null));
    }

    /**
     * @param entityClass the entityView class of the statistic we want to represent
     */
    public NumbersStatisticView(final Class<? extends EntityView> entityClass) {
        super();
        NUMBER_SPRITES.entrySet().stream().forEach(e -> numbers.put(e.getKey(), this.resize(e.getValue())));
        if (entityClass.equals(BombView.class)) {
            img = Optional.of(super.resize(BombView.getBombSprite()));
        } else if (entityClass.equals(KeyView.class)) {
            img = Optional.of(super.resize(KeyView.getKeySprite()));
        }
    }

    /**
     * @return the numbers of times as a list of digits
     */
    private List<Integer> getDigitList() {
        List<Integer> digitList = new LinkedList<>();
        int num = (int) this.getNumber();
        do {
            digitList.add(num % 10);
            num /= 10;
        } while  (num > 0);
        return digitList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        if (!img.isPresent()) {
            throw new IllegalStateException();
        }
        final int y = super.getIndex() * super.getDelta() + super.getMargin();
        gc.drawImage(img.get(), super.getMargin(), y);
        final List<Image> numbers = new LinkedList<>();
        this.getDigitList().stream().forEach(d -> numbers.add(this.numbers.get(d)));
        for (int i = 0; i < numbers.size(); i++) {
            gc.drawImage(numbers.get(i), super.getMargin() + (super.getDelta() * (i + 1)), y);
        }
    }
}
