package view.javafx;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import view.ConfigurationManager;
import view.MenuSelection;

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


    /**
     * Initialize the menu.
     * @param s the scene. It is used for the input.
     */
    public void start(final Scene s) {
        menu = new MenuSelection();
        pnMainMenu.prefWidthProperty().bind(pnMain.widthProperty());
        pnMainMenu.prefHeightProperty().bind(pnMain.heightProperty());
        final MainMenuSelectionJavafx mainMenu = new MainMenuSelectionJavafx(pnMainMenu, s, TIME_SUBMENU, TIME_MENU, manager);
        menu.add(mainMenu);
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

