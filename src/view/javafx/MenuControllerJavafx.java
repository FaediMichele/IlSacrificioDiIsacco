package view.javafx;

import controller.menu.ConfigurationManager;
import controller.menu.GameSubMenuSelection;
import controller.menu.MainMenuSelection;
import controller.menu.MenuSelection;
import controller.menu.SubMenuSelection;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import view.javafx.menu.GameIntroJavafx;

/**
 * This is the class that handle the main menu of javafx.
 */
public class MenuControllerJavafx {
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
        ViewGetterUtil.setScene(s);
        menu = new MenuSelection(manager);
        pnMainMenu.prefWidthProperty().bind(pnMain.widthProperty());
        pnMainMenu.prefHeightProperty().bind(pnMain.heightProperty());
        pnIntro.prefWidthProperty().bind(pnMain.widthProperty());
        pnIntro.prefHeightProperty().bind(pnMain.heightProperty());
        pnGame.prefWidthProperty().bind(pnMain.widthProperty());
        pnGame.prefHeightProperty().bind(pnMain.heightProperty());
        final SubMenuSelection mainMenu = new MainMenuSelection(menu, TIME_MENU);
        final SubMenuSelection intro = new GameIntroJavafx(menu, TIME_MENU);
        final SubMenuSelection game = new GameSubMenuSelection(menu, TIME_MENU);
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

