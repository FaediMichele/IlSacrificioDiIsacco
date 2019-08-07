package view.javafx.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/*import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;*/
import javafx.scene.paint.ImagePattern;

/**
 * It generates the background of the room with the correct doors.
 */
public class RoomView {
    private final Image imageRoom;
    private final Set<DoorView> doors;
    /**
     * @param path the path of the background of the room
     * @param doors the doors of the Room
     * @throws IOException 
     */
    public RoomView(final String path, final Set<DoorView> doors) throws IOException {
        final BufferedImage img = ImageIO.read(RoomView.class.getResource(path));
        this.imageRoom = SwingFXUtils.toFXImage(img, null);
        this.doors = doors;
    }

    /**
     * 
     * @param graphic the {@link GraphicsContext}
     */
    public void draw(final GraphicsContext graphic) {
        //final BackgroundImage backImg = new BackgroundImage(this.imageRoom, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        graphic.getCanvas().getScene().setFill(new ImagePattern(this.imageRoom));
        this.doors.forEach(d -> d.draw(graphic));
    }

}
