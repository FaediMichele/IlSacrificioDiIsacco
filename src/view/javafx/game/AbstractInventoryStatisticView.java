package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class to draw all the StatisticView that needs the icon of the item and the number of items 
 * of that kind in the inventory.
 */
public abstract class AbstractInventoryStatisticView extends AbstractStatisticView {
    private static final Map<Integer, Image> NUMBER_SPRITES = new HashMap<>();
    private static final int INNER_SPACE = 2;

    static {
        BufferedImage img;
        try {
            img = ImageIO.read(MonstroView.class.getResource("/gameImgs/font.png"));
            AbstractInventoryStatisticView.initNumbers(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Image itemImage;
    private final Map<Integer, Image> numbers = new HashMap<>();

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
    * @param itemImage the image of the item to graphic
    */
   public AbstractInventoryStatisticView(final Image itemImage) {
       super();
       this.itemImage = itemImage;
       NUMBER_SPRITES.entrySet().stream().forEach(e -> numbers.put(e.getKey(), e.getValue()));
   }

    /**
     * @return the numbers of times as a list of digits
     */
    private List<Integer> getDigitList() {
        final List<Integer> digitList = new LinkedList<>();
        if (this.getNumber() == 0) {
            digitList.add(0);
            return digitList;
        }
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
        final double numbersDelta = super.getDelta() * 2 / 3;
        final double numbersShift = super.getDelta() * 1 / 4;
        final int y = super.getIndex() * super.getDelta() + super.getMargin();

        gc.drawImage(itemImage, super.getMargin(), y, super.getDelta(), super.getDelta());
        final List<Image> numbersToDraw = new LinkedList<>();
        this.getDigitList().stream().forEach(d -> numbersToDraw.add(this.numbers.get(d)));
        for (int i = 0; i < numbersToDraw.size(); i++) {
            gc.drawImage(numbersToDraw.get(i), super.getMargin() + (super.getDelta() * (i + 1)) + INNER_SPACE, 
                            y + numbersShift, numbersDelta, numbersDelta);
        }
    }
}
