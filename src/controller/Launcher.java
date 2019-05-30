package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.MenuController;

/**
 * Controller for the main menu.
 */
public class Launcher extends Application {
    private static final String WINDOW_NAME = "Il sacrificio di Isacco";
    private static final String FXML_PATH = "/fxml/MainMenu.fxml";


    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        final double maxX = primaryScreenBounds.getMaxX();
        final double maxY = primaryScreenBounds.getMaxY();
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FXML_PATH));
        final Parent root = loader.load();
        ((MenuController) loader.getController()).start();
        final Scene scene = new Scene(root, maxX, maxY);
        stage.setTitle(WINDOW_NAME);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    /**
     * Launch the main menu.
     * @param args not used.
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
