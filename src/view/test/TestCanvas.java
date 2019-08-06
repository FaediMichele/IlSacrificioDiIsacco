package view.test;

import java.util.HashSet;
import java.util.UUID;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.enumeration.BasicMovementEnum;
import view.javafx.game.GameViewImpl;
import view.javafx.game.GaperView;
import view.javafx.game.IsaacView;
import view.javafx.game.RoomView;

/**
 * 
 *
 */
public class TestCanvas extends Application {

    @Override
    public final void start(final Stage stage) throws Exception {
        final RoomView room = new RoomView("/gameImgs/basement_background1_640x344.png", new HashSet<>());
        final Canvas canvas = new Canvas();
        final Pane root = new Pane(canvas);
        final IsaacView isaac = new IsaacView(UUID.randomUUID());
        isaac.setX(300.0);
        isaac.setY(300.0);
        isaac.setHeight(85);
        isaac.setWidth(85);
        isaac.setGameView(new GameViewImpl(root));
        isaac.def(BasicMovementEnum.DOWN);
        final GaperView gaper = new GaperView(UUID.randomUUID());
        gaper.setX(400.0);
        gaper.setY(400.0);
        gaper.setHeight(85);
        gaper.setWidth(85);
        gaper.setGameView(new GameViewImpl(root));
        gaper.def(BasicMovementEnum.DOWN);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                room.draw(canvas.getGraphicsContext2D());
                isaac.draw(canvas.getGraphicsContext2D());
                gaper.draw(canvas.getGraphicsContext2D());
            }
        });
        root.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.getWidth());
            canvas.setHeight(newValue.getHeight());
        });
        final Scene s = new Scene(root);
        s.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {
                final KeyCode key = keyEvent.getCode();
                if (key == KeyCode.W) {
                    isaac.def(BasicMovementEnum.UP);
                    isaac.setY(isaac.getY() - 1);
                } else if (key == KeyCode.D) {
                    isaac.def(BasicMovementEnum.RIGHT);
                    isaac.setX(isaac.getX() + 1);
                } else if (key == KeyCode.S) {
                    isaac.def(BasicMovementEnum.DOWN);
                    isaac.setY(isaac.getY() + 1);
                } else if (key == KeyCode.A) {
                    isaac.def(BasicMovementEnum.LEFT);
                    isaac.setX(isaac.getX() - 1);
                } else {
                    isaac.dead(BasicMovementEnum.DOWN);
                }
                System.out.println(key);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        room.draw(canvas.getGraphicsContext2D());
                        isaac.draw(canvas.getGraphicsContext2D());
                        gaper.draw(canvas.getGraphicsContext2D());
                    }
                });
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        stage.setScene(s);
        stage.show();
    }

    /**
     * 
     * @param args 
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
