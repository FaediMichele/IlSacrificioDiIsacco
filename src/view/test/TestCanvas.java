package view.test;

import java.util.HashSet;
import java.util.UUID;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.enumeration.BasicMovementEnum;
import view.javafx.game.GameViewImpl;
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
        isaac.setX(20.0);
        isaac.setY(20.0);
        isaac.setHeight(100);
        isaac.setWidth(100);
        isaac.setGameView(new GameViewImpl(root));
        isaac.def(BasicMovementEnum.UP);
        
        
        
        //root.setPrefSize(600, 800);
        
        root.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.getWidth());
            canvas.setHeight(newValue.getHeight());
            room.draw(canvas.getGraphicsContext2D());
            isaac.draw(canvas.getGraphicsContext2D());
        });
        stage.setScene(new Scene(root));
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
