package view.javafx.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Implementation of the StatisticView.
 */
public abstract class AbstractStatisticView implements StatisticView {

    private static final int MARGIN = 2;
    private static final int DEFAULT_DELTA = 30;

    private double itemsNumber;
    private final int delta;
    private final int index;

    /**
     * @param index the position in the GameView list of statistics
     */
    protected AbstractStatisticView(final int index) {
        this(index, DEFAULT_DELTA);
    }

    /**
     * @param index the position in the GameView list of statistics
     * @param delta the size of a side of the square that will contain the sprite
     */
    protected AbstractStatisticView(final int index, final int delta) {
        this.delta = delta;
        this.index = index;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public double getNumber() {
        return itemsNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNumber(final double number) {
        this.itemsNumber = number;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void draw(GraphicsContext gc);

    /**
     * @param img image to resize
     * @return resized image
     */
    protected Image resize(final Image img) {
        final ImageView resizedImage = new ImageView(img);
        resizedImage.setFitHeight(delta);
        resizedImage.setFitWidth(delta);
        return resizedImage.snapshot(null, null);
    }

    /**
     * @return the Margin from the top
     */
    protected static int getMargin() {
        return MARGIN;
    }

    /**
     * @return the index of the statstics in the GameView statstics list
     */
    protected int getIndex() {
        return index;
    }

    /**
     * @return the delta (size of the sprite square)
     */
    public int getDelta() {
        return delta;
    }

}
