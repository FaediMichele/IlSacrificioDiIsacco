package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import view.javafx.SubMenuEnter;
import view.javafx.SubMenuGame;
import view.javafx.SubMenuSelectionJavafx;

/**
 * This is the class that handle the main menu of javafx.
 *
 */
public class MenuController {
    private final SubMenuSelection menu = new SubMenuSelectionJavafx(500);

    @FXML private ImageView imgNameOfGame;
    @FXML private ImageView imgIsaac;
    @FXML private Pane pnMain;
    @FXML private Pane pnEnter;
    @FXML private Pane pnRun;
    @FXML private Pane pnGame;
    @FXML private ImageView imgNewRun;
    /*
    @FXML private ImageView imgHeart;
    @FXML private ImageView imgSpeed;
    @FXML private ImageView imgDamage;
    */
    @FXML private ImageView imgOptions;
    @FXML private ImageView imgSelector;

    /**
     * Initialize the menu.
     * @param s the scene. It is used for the input.
     */
    public void start(final Scene s) {
        pnMain.focusedProperty().addListener(b -> {
            if (pnMain.isFocusTraversable()) {
                pnMain.requestFocus();
            }
        });
        pnMain.setOnKeyPressed(k -> {
            menu.get().enter();
        });
        Platform.runLater(() -> pnMain.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateSize(pnMain, new Rectangle2D(0d, 0d, (double) oldVal,
                    pnMain.getHeight()), new Rectangle2D(0d, 0d, (double) newVal, pnMain.getHeight()));
        }));
        Platform.runLater(() -> pnMain.heightProperty().addListener((obs, oldVal, newVal) -> {
            updateSize(pnMain, new Rectangle2D(0d, 0d, pnMain.getWidth(),
                    (double) oldVal), new Rectangle2D(0d, 0d, pnMain.getWidth(), (double) newVal));
        }));
        pnMain.requestFocus();

        menu.add(new SubMenuEnter(menu, pnEnter, imgNameOfGame, imgIsaac));
        menu.add(new SubMenuGame(menu, pnGame, imgNewRun, imgOptions, imgSelector));
    }

    private void updateSize(final Parent p, final Rectangle2D oldValue, final Rectangle2D newValue) {
        p.getChildrenUnmodifiable().forEach(n -> {
            n.setLayoutX(n.getLayoutX() * newValue.getWidth()
                    / oldValue.getWidth());
            n.setLayoutY(n.getLayoutY() * newValue.getHeight()
                    / oldValue.getHeight());
            if (n instanceof Control) {
                final Control c = (Control) n;
                c.setPrefWidth(c.getPrefWidth() * newValue.getWidth()
                        / oldValue.getWidth());
                c.setPrefHeight(c.getPrefHeight() * newValue.getHeight()
                        / oldValue.getHeight());
            } else if (n instanceof ImageView) {
                final ImageView c = (ImageView) n;
                c.setFitWidth(c.getFitWidth() * newValue.getWidth()
                        / oldValue.getWidth());
                c.setFitHeight(c.getFitHeight() * newValue.getHeight()
                        / oldValue.getHeight());
            }
            if (n instanceof Parent) {
                updateSize((Parent) n, oldValue, newValue);
            }
        });
    }
}
