package view.javafx.game;

import java.util.Optional;
import javafx.scene.canvas.GraphicsContext;

/**
 * Implementation of the StatisticView.
 */
public abstract class AbstractStatisticView implements StatisticView {

    private static final int MARGIN = 2;
    private static final int DEFAULT_DELTA = 30;
    private Optional<Double> itemsNumber = Optional.empty();
    private Optional<Integer> index = Optional.empty();

    private final int delta;

    /**
     * Basic constructor that uses the default delta.
     */
    protected AbstractStatisticView() {
        this(DEFAULT_DELTA);
    }

    /**
     * @param delta the size of a side of the square that will contain the sprite
     */
    protected AbstractStatisticView(final int delta) {
        this.delta = delta;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public double getNumber() {
        if (!this.itemsNumber.isPresent()) {
            throw new IllegalStateException();
        }
        return itemsNumber.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNumber(final double number) {
        this.itemsNumber = Optional.of(number);
    }

    /**
     * @return the Margin from the top
     */
    protected static int getMargin() {
        return MARGIN;
    }

    /**
     * @return the index of the statstic in the GameView statstics list
     */
    protected int getIndex() {
        if (!index.isPresent()) {
            throw new IllegalStateException();
        }
        return index.get();
    }

    /**
     * {@inheritDoc}
     */
    public void setIndex(final int index) {
        this.index = Optional.of(index);
    }

    /**
     * @return the delta (size of the sprite square)
     */
    public int getDelta() {
        return delta;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void draw(GraphicsContext gc);
}
