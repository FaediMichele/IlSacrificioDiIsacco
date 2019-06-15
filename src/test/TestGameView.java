package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 *
 */
public class TestGameView extends Application {

    /**
     * Test for the interface.
     * 
     * @param args not used.
     * @throws IOException exception
     */
    public static void main(final String[] args) throws IOException {
        launch(args);
    }

    @Override
    public final void start(final Stage stage) throws Exception {
        BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/character_001_isaac.png"));
        final int deltaFace = 32;
        final int deltaBody = 32;
        final int coorBodyX = 197;
        final int coordBodyY = 10;
        final int col = 8;
        final int faces = 6;
        final int rows = 3;
        final int extras = 2;
        List<BufferedImage> sprites = new ArrayList<>();
        // Facce
        for (int i = 0; i < faces; i++) {
            sprites.add(img.getSubimage(i * deltaFace, 0, deltaFace, deltaFace));
        }
        sprites.add(img.getSubimage(deltaFace * faces, 0, deltaBody, deltaBody));
        sprites.add(img.getSubimage(deltaFace * faces + deltaBody, 0, deltaBody, deltaBody));
        for (int i = 0; i < sprites.size(); i++) {
            String name = i + ".jpg";
            ImageIO.write(sprites.get(i), "jpg", new File(name));
        }
        final Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Game.fxml"));
        final Scene scene = new Scene(parent);
        stage.setTitle("Il sacrificio di Isacco");
        stage.setScene(scene);
        stage.show();

    }
}
