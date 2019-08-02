package view.test;

import java.util.HashSet;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import view.javafx.game.RoomView;

/**
 * 
 *
 */
public class TestCanvas extends Application {

    @Override
    public final void start(final Stage stage) throws Exception {
        final RoomView room = new RoomView("/gameImgs/basement_background1_640x344.png", new HashSet<>());
        final Group root = new Group();
        final Canvas canvas = new Canvas();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
        room.draw(canvas.getGraphicsContext2D());
    }

    /**
     * 
     * @param args 
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
