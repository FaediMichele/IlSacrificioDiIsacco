package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.enumeration.BasicMovementEnum;
import model.enumeration.MovementEnum;

/**
 * 
 *
 */
public class DoorView extends AbstractEntityView {

    private static Image doorOper;
    private static Image doorLocked;
    private static Image doorClosed;

    private Image door;
    static {
        try {
            final BufferedImage img = ImageIO.read(DoorView.class.getResource("/gameImgs/door.png"));
            doorOper = SwingFXUtils.toFXImage(img, null);
            final BufferedImage imgLocked = ImageIO.read(DoorView.class.getResource("/gameImgs/door_locked.png"));
            doorLocked = SwingFXUtils.toFXImage(imgLocked, null);
            final BufferedImage imgClosed = ImageIO.read(DoorView.class.getResource("/gameImgs/door_closed.png"));
            doorClosed = SwingFXUtils.toFXImage(imgClosed, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param id the {@link UUID}
     * @param gv {@link GameView}
     */
    public DoorView(final UUID id, final GameViewImpl gv) {
        super(id, gv);
    }

    /**
     * 
     * @param id the {@link UUID}
     */
    public DoorView(final UUID id) {
        super(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final GraphicsContext gc) {
        gc.drawImage(door, super.getX(), super.getY(), super.getWidth(), super.getHeight());
    }

    /**
     * @param pos the position of the door
     */
    public void locked(final MovementEnum pos) {
        if (pos == BasicMovementEnum.UP) {
            this.door = doorLocked;
        } else if (pos == BasicMovementEnum.DOWN) {
            this.door = doorLocked;
        } else if (pos == BasicMovementEnum.RIGHT) {
            this.door = doorLocked;
        } else if (pos == BasicMovementEnum.LEFT) {
            this.door = doorLocked;
        }
    }

    /**
     * @param pos the position of the door
     */
    public void closed(final MovementEnum pos) {
        if (pos == BasicMovementEnum.UP) {
            this.door = doorClosed;
        } else if (pos == BasicMovementEnum.DOWN) {
            this.door = doorClosed;
        } else if (pos == BasicMovementEnum.RIGHT) {
            this.door = doorClosed;
        } else if (pos == BasicMovementEnum.LEFT) {
            this.door = doorClosed;
        }
    }

    /**
     * @param pos the position of the door
     */
    public void open(final MovementEnum pos) {
        if (pos == BasicMovementEnum.UP) {
            this.door = doorOper;
        } else if (pos == BasicMovementEnum.DOWN) {
            this.door = doorOper;
        } else if (pos == BasicMovementEnum.RIGHT) {
            this.door = doorOper;
        } else if (pos == BasicMovementEnum.LEFT) {
            this.door = doorOper;
        }
    }

}
