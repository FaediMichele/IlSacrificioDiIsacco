package view.javafx.game;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.enumeration.BasicStatusEnum;
import model.enumeration.MovementEnum;

/**
 * Class to draw all kinds of tears.
 */
public class AbstractTearView extends AbstractEntityView {

    private final List<Image> tears;
    private int index;

    AbstractTearView(final List<Image> tears, final GameView gameView) {
        super(gameView);
        this.tears = tears;
        this.index = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        gc.drawImage(this.tears.get(index), super.getX(), super.getY(), super.getHeight(), super.getWidth());
        if (this.getStatus().isPresent() && this.getStatus().get().equals(BasicStatusEnum.DISAPPEAR)) {
            index += 1;
            if (index >= this.tears.size()) {
                super.disappear(null);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disappear(final MovementEnum move) {
        this.setStatus(BasicStatusEnum.DISAPPEAR);
    }

}
