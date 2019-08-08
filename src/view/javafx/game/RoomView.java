package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * It generates the background of the room with the correct doors.
 */
public class RoomView {
    private final Image imageRoom;
    private final Set<DoorView> doors = new HashSet<DoorView>();;
    /**
     * @param path the path of the background of the room
     * @param doors the doors of the Room
     * @throws IOException 
     */
    public RoomView(final String path, final Set<DoorView> doors) throws IOException {
        final BufferedImage img = ImageIO.read(RoomView.class.getResource(path));
        this.imageRoom = SwingFXUtils.toFXImage(img, null);
        if (doors != null) {
            this.doors.addAll(doors);
        }
    }

    /**
     * 
     * @param graphic the {@link GraphicsContext}
     */
    public void draw(final GraphicsContext graphic) {
        graphic.drawImage(this.imageRoom, 0, 0, graphic.getCanvas().getWidth(), graphic.getCanvas().getHeight());
        this.doors.forEach(d -> d.draw(graphic));
    }

}
