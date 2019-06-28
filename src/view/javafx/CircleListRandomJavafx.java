package view.javafx;

import java.util.Random;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * This circle list have a initial node that randomize the selection when selected.
 */
public class CircleListRandomJavafx extends CircleListJavafx {
    private final Node random;
    private final long ms;
    private static final int MULTIPLIER = 10;

    /**
     * Create a new {@link CircleListRandomJavafx} with dimension and scaleMultiplier. With an initial node for the random search.
     * @param width the with of the ellipse of elements
     * @param height the height of the ellipse of elements
     * @param scaleMultiplier a scale multiplier for elements(far elements is smaller).
     * @param duration the time of the animation.
     * @param random the {@link Node} to use for the random.
     */
    public CircleListRandomJavafx(final double width, final double height, final double scaleMultiplier, final Object duration,
            final Node random) {
        super(width, height, scaleMultiplier);
        this.random = random;
        super.setDuration(duration);
        ms = (long) ((Duration) duration).toMillis();
        addAll(random);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getElement() {
        final Object obj = super.getElement();
        if (obj.equals(random)) {
            final int index = new Random().nextInt(size() * 2  - size() - 1) + size() + 1;
            final Object o = super.getElement(index % size());
            (new Thread() { public void run() {
                try {
                    int i = index;
                    while (i >= 0) {
                        Platform.runLater(() -> rotateLeft());
                        Thread.sleep(MULTIPLIER * ms / (MULTIPLIER - 1));
                        i--;
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
