package test;

import controller.GameController;
import controller.menu.GameSubMenuSelection;
import controller.menu.MenuSelection;
import controller.menu.SubMenuGame;
import controller.menu.SubMenuSelection;
import javafx.application.Application;
import javafx.stage.Stage;
import view.javafx.ConfigurationManagerJavafx;

/**
 * Class for testing the game.
 */
public class TestGame extends Application {
    /**
     * 
     */
    @Override
    public void start(final Stage arg0) throws Exception {
        final MenuSelection sel = new MenuSelection(new ConfigurationManagerJavafx("/config.ini"));
        final SubMenuSelection subMenu = new GameSubMenuSelection(sel, 100);
        final SubMenuGame gameMenu = new SubMenuGame(subMenu);
        final GameController controller = new GameController(gameMenu);
        controller.start();
    }

    /**
     * Launch the main menu.
     * 
     * @param args not used.
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
