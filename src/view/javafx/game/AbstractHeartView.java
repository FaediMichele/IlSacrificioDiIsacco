package view.javafx.game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.enumeration.BasicStatusEnum;
import model.enumeration.MovementEnum;

/**
* View of the hearts.
*/
public class AbstractHeartView extends AbstractEntityView {

    private Image heart;
    private Image halfHeart;

    /**
     * Base constructor, initilizes the indexes and sets the images to used based on the colour of the heart.
     * @param heart the image of the full heart
     * @param halfHeart the image of the half heart
     */
    AbstractHeartView(final Image heart, final Image halfHeart) {
        this.halfHeart = halfHeart;
        this.heart = heart;
    }

    /**
     * {@inheritDoc}
     */
    public void draw(final GraphicsContext gc) {
        if (super.getStatus().isPresent() && super.getStatus().get().equals(BasicStatusEnum.FULL)) {
            gc.drawImage(heart, super.getX(), super.getY(), super.getHeight(), super.getWidth());
        }

        if (super.getStatus().isPresent() && super.getStatus().get().equals(BasicStatusEnum.HALF)) {
            gc.drawImage(halfHeart, super.getX(), super.getY(), super.getHeight(), super.getWidth());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void full(final MovementEnum move) {
        super.setStatus(BasicStatusEnum.FULL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void half(final MovementEnum move) {
        super.setStatus(BasicStatusEnum.HALF);
    }


}
