package view.javafx;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import controller.menu.ConfigurationManager;
import controller.menu.GameSubMenuSelection;
import controller.menu.MainMenuSelection;
import controller.menu.MenuSelection;
import controller.menu.SubMenuSelection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import view.javafx.menu.GameIntroJavafx;

/**
 * This is the class that handle the main menu of javafx.
 */
public class MenuControllerJavafx {
    private static final long TIME_MENU = 500;

    private MenuSelection menu;
    private final ConfigurationManager manager = new ConfigurationManagerJavafx("/config.ini");
    private final Set<KeyCode> keyPressed = new LinkedHashSet<>();

    @FXML private Pane pnMain;
    @FXML private Pane pnMainMenu;
    @FXML private Pane pnIntro;
    @FXML private Pane pnGame;
    @FXML private MediaView mvIntro;

    /**
     * Initialize the menu.
     * @param s the scene. It is used for the input.
     * @param stage the stage. It is used for the event when the application is closed
     */
    public void start(final Scene s, final Stage stage) {
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
        s.setOnKeyPressed(k -> {
            keyPressed.add(k.getCode());
            menu.get().get().input(keyPressed.stream().map(key -> manager.getKeyMap().get(key)).filter(c -> c != null).collect(Collectors.toSet()));
        });
        s.setOnKeyReleased(k -> {
            keyPressed.remove(k.getCode());
            menu.get().get().input(keyPressed.stream().map(key -> manager.getKeyMap().get(key)).collect(Collectors.toSet()));
        });
        stage.setOnCloseRequest(we -> menu.onClose());
        pnMain.requestFocus();
    }
}

