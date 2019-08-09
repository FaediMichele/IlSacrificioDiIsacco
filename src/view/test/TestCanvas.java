package view.test;

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
import model.entity.SimplePickupableHeart;
import model.enumeration.BasicMovementEnum;
import view.javafx.game.DoorView;
import view.javafx.game.GameViewImpl;
import view.javafx.game.GaperView;
import view.javafx.game.IsaacView;
import view.javafx.game.KeyView;
import view.javafx.game.RedHeartView;
import view.javafx.game.RockView;
import view.javafx.game.AbstractPlayerView;
import view.javafx.game.RoomView;

/**
 * 
 *
 */
public class TestCanvas extends Application {

    @Override
    public final void start(final Stage stage) throws Exception {
        final RoomView room = new RoomView("/gameImgs/basement_background1_640x344.png");
        final Canvas canvas = new Canvas();
        final Pane root = new Pane(canvas);
        final AbstractPlayerView isaac = new IsaacView(UUID.randomUUID());
        isaac.setX(300.0);
        isaac.setY(300.0);
        isaac.setHeight(85);
        isaac.setWidth(85);
        isaac.setGameView(new GameViewImpl(canvas));
        isaac.def(BasicMovementEnum.DOWN);
        final GaperView gaper = new GaperView(UUID.randomUUID());
        gaper.setX(400.0);
        gaper.setY(400.0);
        gaper.setHeight(85);
        gaper.setWidth(85);
        gaper.setGameView(new GameViewImpl(canvas));
        gaper.def(BasicMovementEnum.DOWN);
        final KeyView rock = new KeyView(UUID.randomUUID());
        rock.setX(200.0);
        rock.setY(200.0);
        rock.setHeight(30);
        rock.setWidth(30);
        rock.setGameView(new GameViewImpl(canvas));
        final DoorView door = new DoorView(UUID.randomUUID());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                door.setX((canvas.getWidth() - 100) / 2);
                door.setY(15);
            }
        });

        door.setHeight(85);
        door.setWidth(85);
        door.setGameView(new GameViewImpl(canvas));
        door.locked(BasicMovementEnum.UP);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                room.draw(canvas.getGraphicsContext2D());
                isaac.draw(canvas.getGraphicsContext2D());
                gaper.draw(canvas.getGraphicsContext2D());
                door.draw(canvas.getGraphicsContext2D());
                rock.draw(canvas.getGraphicsContext2D());
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
                        door.draw(canvas.getGraphicsContext2D());
                        rock.draw(canvas.getGraphicsContext2D());
                    }
                });
                try {
                    Thread.sleep(40);
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
