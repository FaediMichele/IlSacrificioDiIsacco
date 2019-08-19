package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * It generates the background of the room with the correct doors.
 */
public class RoomView {
    private static final String PATH_DEFOULT_ROOM = "/gameImgs/basement_background1_640x344.png";
    private final Image imageRoom;
    /**
     * @param path the path of the background of the room
     * @throws IOException 
     */
    public RoomView(final String path) throws IOException {
        final BufferedImage img = ImageIO.read(RoomView.class.getResourceAsStream(path));
        this.imageRoom = SwingFXUtils.toFXImage(img, null);
    }

    /**
     * Default constructor.
     * @throws IOException 
     */
    public RoomView() throws IOException {
        final BufferedImage img = ImageIO.read(RoomView.class.getResourceAsStream(PATH_DEFOULT_ROOM));
        this.imageRoom = SwingFXUtils.toFXImage(img, null);
    }
    /**
     * 
     * @param graphic the {@link GraphicsContext}
     */
    public void draw(final GraphicsContext graphic) {
        graphic.drawImage(this.imageRoom, 0, 0, graphic.getCanvas().getWidth(), graphic.getCanvas().getHeight());
    }

}
