package view.javafx;

import java.util.Random;

import javafx.application.Platform;
import javafx.scene.Node;

/**
 * This circle list have a initial node that randomize the selection when selected.
 */
public class CircleListRandomJavafx extends CircleListJavafx {
    private final Node random;
    private final long msRandomize;

    /**
     * Create a new {@link CircleListRandomJavafx} with dimension and scaleMultiplier. With an initial node for the random search.
     * @param width the with of the ellipse of elements
     * @param height the height of the ellipse of elements
     * @param scaleMultiplier a scale multiplier for elements(far elements is smaller).
     * @param duration the time of the animation.
     * @param random the {@link Node} to use for the random.
     * @param msRandomize time for the animation for the randomize.
     */
    public CircleListRandomJavafx(final double width, final double height, final double scaleMultiplier, final Object duration,
            final Node random, final long msRandomize) {
        super(width, height, scaleMultiplier);
        this.random = random;
        super.setDuration(duration);
        this.msRandomize = msRandomize;
        addAll(random);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getElement() {
        final Object obj = super.getElement();
        if (obj.equals(random)) {
            final int index = new Random().nextInt(size() - 1) + 1;
            final Object o = super.getElement(index);
            (new Thread() { public void run() {
                try {
                    int i = index;
                    if (i < size() / 2) {
                        while (i >= 0) {
                            Platform.runLater(() -> rotateLeft());
                            i--;
                            Thread.sleep(msRandomize);
                        }
                    } else {
                        while (i < size()) {
                            Platform.runLater(() -> rotateRight());
                            i++;
                            Thread.sleep(msRandomize);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            }).start();
            return o;
        }
        return obj;
    }
}
