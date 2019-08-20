package view.javafx;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import controller.menu.ConfigurationManager;
import controller.menu.IntroSelection;
import controller.menu.GameMenuSelection;
import controller.menu.InputMenu;
import controller.menu.MainMenuSelection;
import controller.menu.Root;
import controller.menu.SubMenu;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 * This is the class that handle the main menu of javafx.
 */
public class ControllerJavafx {
    private static final long TIME_MENU = 500;

    private Root menu;
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
        ViewGetterUtil.setStage(stage);
        menu = new Root(manager);
        pnMainMenu.prefWidthProperty().bind(pnMain.widthProperty());
        pnMainMenu.prefHeightProperty().bind(pnMain.heightProperty());
        pnIntro.prefWidthProperty().bind(pnMain.widthProperty());
        pnIntro.prefHeightProperty().bind(pnMain.heightProperty());
        pnGame.prefWidthProperty().bind(pnMain.widthProperty());
        pnGame.prefHeightProperty().bind(pnMain.heightProperty());
        final InputMenu<SubMenu> mainMenu = new MainMenuSelection(TIME_MENU);
        final InputMenu<SubMenu> intro = new IntroSelection(TIME_MENU);
        final InputMenu<SubMenu> game = new GameMenuSelection(TIME_MENU);
        menu.add(mainMenu);
        menu.add(intro);
        menu.add(game);
        menu.select(IntroSelection.class);
        pnMain.focusedProperty().addListener(b -> {
            if (pnMain.isFocusTraversable()) {
                pnMain.requestFocus();
            }
        });
        s.setOnKeyPressed(k -> {
            keyPressed.add(k.getCode());
            menu.getSelected().input(keyPressed.stream().filter(key -> manager.getKeyMap().containsKey(key)).map(key -> manager.getKeyMap().get(key)).collect(Collectors.toSet()));
        });
        s.setOnKeyReleased(k -> {
            keyPressed.remove(k.getCode());
            menu.getSelected().input(keyPressed.stream().filter(key -> manager.getKeyMap().containsKey(key)).map(key -> manager.getKeyMap().get(key)).collect(Collectors.toSet()));
        });
        stage.setOnCloseRequest(we -> menu.close());
        pnMain.requestFocus();
    }
}

