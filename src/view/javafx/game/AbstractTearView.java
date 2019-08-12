package view.javafx.game;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class to draw all kinds of tears.
 */
public class AbstractTearView extends AbstractEntityView {

    private final List<Image> tears;
    private int index;

    AbstractTearView(final List<Image> tears) {
        super();
        this.tears = tears;
        this.index = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        gc.drawImage(this.tears.get(index), super.getX(), super.getY(), super.getHeight(), super.getWidth());
        index += 1;
        if (index >= this.tears.size()) {
            index -= 3;
        }
    }

}
