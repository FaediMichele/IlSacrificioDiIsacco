package view.node;

import java.util.Random;

import javafx.scene.image.ImageView;

/**
 * This circle list have a initial node that randomize the selection when selected.
 */
public class CircleListRandomJavafx extends CircleListJavafx {
    private final ImageView random;

    /**
     * Create a new {@link CircleListRandomJavafx} with dimension and scaleMultiplier. With an initial node for the random search.
     * @param width the with of the ellipse of elements
     * @param height the height of the ellipse of elements
     * @param scaleMultiplier a scale multiplier for elements(far elements is smaller).
     * @param random the {@link ImageView} to use for the random.
     */
    public CircleListRandomJavafx(final double width, final double height, final double scaleMultiplier, final ImageView random) {
        super(width, height, scaleMultiplier);
        this.random = random;
        addAll(random);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getElement() {
        final Object obj = super.getElement();
        if (obj.equals(random)) {
            final Random rnd = new Random();
            final int index = rnd.nextInt(size() - 1) + 1;
            int i = index;
            if (index < size() / 2) {
                while (i >= 0) {
                    rotateLeft();
                    i--;
                }
            } else {
                while (i < size()) {
                    rotateRight();
                    i++;
                }
            }
            return getElement(index);
        }
        return obj;
    }

}
