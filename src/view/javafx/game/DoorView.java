package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.enumeration.BasicMovementEnum;
import model.enumeration.MovementEnum;
import util.StaticMethodsUtils;

/**
 * 
 *
 */
public class DoorView extends AbstractEntityView {
    private static final Map<MovementEnum, Image> DOOR_OPEN = new HashMap<>();
    private static final Map<MovementEnum, Image> DOOR_CLOSE = new HashMap<>();
    private static final Map<MovementEnum, Image> DOOR_LOCKED = new HashMap<>();

    private Image door;

    static {
        try {
            final BufferedImage img = ImageIO.read(DoorView.class.getResource("/gameImgs/door.png"));
            BufferedImage imgRight = StaticMethodsUtils.rotateImageBy90Degrees(img);
            BufferedImage imgDown = StaticMethodsUtils.rotateImageBy90Degrees(imgRight);
            BufferedImage imgLeft = StaticMethodsUtils.rotateImageBy90Degrees(imgDown);

            DOOR_OPEN.put(BasicMovementEnum.UP, SwingFXUtils.toFXImage(img, null));
            DOOR_OPEN.put(BasicMovementEnum.RIGHT, SwingFXUtils.toFXImage(imgRight, null));
            DOOR_OPEN.put(BasicMovementEnum.DOWN, SwingFXUtils.toFXImage(imgDown, null));
            DOOR_OPEN.put(BasicMovementEnum.LEFT, SwingFXUtils.toFXImage(imgLeft, null));

            final BufferedImage imgLocked = ImageIO.read(DoorView.class.getResource("/gameImgs/door_locked.png"));
            imgRight = StaticMethodsUtils.rotateImageBy90Degrees(imgLocked);
            imgDown = StaticMethodsUtils.rotateImageBy90Degrees(imgRight);
            imgLeft = StaticMethodsUtils.rotateImageBy90Degrees(imgDown);

            DOOR_LOCKED.put(BasicMovementEnum.UP, SwingFXUtils.toFXImage(imgLocked, null));
            DOOR_LOCKED.put(BasicMovementEnum.RIGHT, SwingFXUtils.toFXImage(imgRight, null));
            DOOR_LOCKED.put(BasicMovementEnum.DOWN, SwingFXUtils.toFXImage(imgDown, null));
            DOOR_LOCKED.put(BasicMovementEnum.LEFT, SwingFXUtils.toFXImage(imgLeft, null));

            final BufferedImage imgClosed = ImageIO.read(DoorView.class.getResource("/gameImgs/door_closed.png"));
            imgRight = StaticMethodsUtils.rotateImageBy90Degrees(imgClosed);
            imgDown = StaticMethodsUtils.rotateImageBy90Degrees(imgRight);
            imgLeft = StaticMethodsUtils.rotateImageBy90Degrees(imgDown);

            DOOR_CLOSE.put(BasicMovementEnum.UP, SwingFXUtils.toFXImage(imgClosed, null));
            DOOR_CLOSE.put(BasicMovementEnum.RIGHT, SwingFXUtils.toFXImage(imgRight, null));
            DOOR_CLOSE.put(BasicMovementEnum.DOWN, SwingFXUtils.toFXImage(imgDown, null));
            DOOR_CLOSE.put(BasicMovementEnum.LEFT, SwingFXUtils.toFXImage(imgLeft, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        this.door = DOOR_LOCKED.get(pos);
    }

    /**
     * @param pos the position of the door
     */
    public void closed(final MovementEnum pos) {
        this.door = DOOR_CLOSE.get(pos);
    }

    /**
     * @param pos the position of the door
     */
    public void open(final MovementEnum pos) {
        this.door = DOOR_OPEN.get(pos);
    }

}
