package test;

import java.io.IOException;
import javafx.application.Application;
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
        /*BufferedImage img = ImageIO.read(getClass().getResource("/gameImgs/character_001_isaac.png"));
        final int deltaFace = 32;
        final int deltaBody = 32;
        final int faces = 6;
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
        stage.show();*/
      //  new IsaacView();

    }
}
