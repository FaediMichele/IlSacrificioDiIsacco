package view.javafx;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import view.ConfigurationManager;
import view.MenuSelection;
import view.SubMenuSelection;
import view.javafx.game.menu.GameSubMenuSelection;

/**
 * This is the class that handle the main menu of javafx.
 *
 */
public class MenuControllerJavafx {
    private static final long TIME_SUBMENU = 250;
    private static final long TIME_MENU = 500;


    private MenuSelection menu;
    private final ConfigurationManager manager = new ConfigurationManagerJavafx("/config.ini");

    @FXML private Pane pnMain;
    @FXML private Pane pnMainMenu;
    @FXML private Pane pnIntro;
    @FXML private Pane pnGame;
    @FXML private MediaView mvIntro;


    /**
     * Initialize the menu.
     * @param s the scene. It is used for the input.
     */
    public void start(final Scene s) {
        menu = new MenuSelection();
        pnMainMenu.prefWidthProperty().bind(pnMain.widthProperty());
        pnMainMenu.prefHeightProperty().bind(pnMain.heightProperty());
        pnIntro.prefWidthProperty().bind(pnMain.widthProperty());
        pnIntro.prefHeightProperty().bind(pnMain.heightProperty());
        pnGame.prefWidthProperty().bind(pnMain.widthProperty());
        pnGame.prefHeightProperty().bind(pnMain.heightProperty());
        final SubMenuSelection mainMenu = new MainMenuSelectionJavafx(menu, pnMainMenu, s, TIME_SUBMENU, TIME_MENU, manager);
        final SubMenuSelection intro = new GameIntroJavafx(menu, pnIntro, s, mvIntro, TIME_MENU);
        final SubMenuSelection game = new GameSubMenuSelection(menu, pnGame, s, TIME_MENU);
        menu.add(mainMenu, intro, game);
        menu.select(GameIntroJavafx.class);
        pnMain.focusedProperty().addListener(b -> {
            if (pnMain.isFocusTraversable()) {
                pnMain.requestFocus();
            }
        });

        // Input redirection.
        pnMain.setOnKeyPressed(k -> {
            if (manager.getKeyMap().containsKey(k.getCode())) {
                menu.get().get().input(manager.getKeyMap().get(k.getCode()));
            }
        });
        pnMain.requestFocus();
    }
}

