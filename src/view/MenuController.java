package view;

import java.util.LinkedHashMap;
import java.util.Map;

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
import view.node.SelectList;
import view.node.SelectListJavafx;
import view.node.TranslationPageJavafx;
import view.node.TranslationPages;

/**
 * This is the class that handle the main menu of javafx.
 *
 */
public class MenuController {
    private static final String NAMEOFGAME = "/menuImgs/nameOfGame-";
    private static final String PRESSSTART = "/menuImgs/pressStart-";
    private static final String SELECTOR = "/menuImgs/selector.png";
    private static final String NEWRUN = "/menuImgs/newRun.png";
    private static final String OPTIONS = "/menuImgs/options.png";
    private static final String FORMAT = ".png";
    private static final long FRAMETIME_DEFAULT = 300;
    private static final long FRAMETIME_NAMEOFGAME = 150;

    private final TimedViews timeDefault = new TimedViewsJavafx();
    private final TimedViews timeNameOfGame = new TimedViewsJavafx();

    private final TranslationPages tp = new TranslationPageJavafx(500);
    private final SelectList sl = new SelectListJavafx();

    private final Map<Pair<Pane, KeyCode>, Pane> nextPaneOnKey = new LinkedHashMap<>();

    @FXML private ImageView imgNameOfGame;
    @FXML private ImageView imgPressStart;
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
        setTimedViews();
        imgNewRun.setImage(new Image(NEWRUN));
        nextPaneOnKey.put(new Pair<>(pnEnter, KeyCode.ENTER), pnGame);
        nextPaneOnKey.put(new Pair<>(pnRun, KeyCode.ESCAPE), pnGame);
        nextPaneOnKey.put(new Pair<>(pnGame, KeyCode.ESCAPE), pnEnter);
        pnMain.setOnKeyPressed(k -> {
            if (nextPaneOnKey.containsKey(new Pair<Pane, KeyCode>((Pane) tp.getSelected(), k.getCode()))) {
                tp.goTo(nextPaneOnKey.get(new Pair<Pane, KeyCode>((Pane) tp.getSelected(), k.getCode())));
            }
            if (tp.getSelected().equals(pnGame) && k.getCode().equals(KeyCode.DOWN)) {
                sl.next();
            }
            if (tp.getSelected().equals(pnGame) && k.getCode().equals(KeyCode.UP)) {
                sl.previous();
            }
        });
        Platform.runLater(() -> pnMain.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateSize(pnMain, new Rectangle2D(0d, 0d, (double) oldVal,
                    pnMain.getHeight()), new Rectangle2D(0d, 0d, (double) newVal, pnMain.getHeight()));
        }));
        Platform.runLater(() -> pnMain.heightProperty().addListener((obs, oldVal, newVal) -> {
            updateSize(pnMain, new Rectangle2D(0d, 0d, pnMain.getWidth(),
                    (double) oldVal), new Rectangle2D(0d, 0d, pnMain.getWidth(), (double) newVal));
        }));
        imgSelector.setImage(new Image(SELECTOR));
        imgOptions.setImage(new Image(OPTIONS));
        sl.addItems(imgNewRun, imgOptions);
        sl.setDistance(new Pair<Double, Double>(-imgSelector.getBoundsInParent().getWidth(), imgSelector.getBoundsInParent().getHeight()));
        sl.setSelector(imgSelector);

        tp.addPage(pnEnter, pnRun, pnGame);
        tp.goTo(pnEnter);
        pnMain.requestFocus();
    }

    private void setTimedViews() {
        final AnimatedView nameOfGame = new AnimatedViewJavafx(imgNameOfGame);
        final AnimatedView pressStart = new AnimatedViewJavafx(imgPressStart);

        setFrames(nameOfGame, NAMEOFGAME);
        setFrames(pressStart, PRESSSTART);
        timeDefault.add(pressStart);
        timeDefault.setMilliseconds(FRAMETIME_DEFAULT);
        timeNameOfGame.add(nameOfGame);
        timeNameOfGame.setMilliseconds(FRAMETIME_NAMEOFGAME);
        timeDefault.start();
        timeNameOfGame.start();
    }

    private void setFrames(final AnimatedView av, final String initialName) {
        int index = 0;
        while (getClass().getResource(initialName + index + FORMAT) != null) {
            av.setFrames(new Image(initialName + index + FORMAT));
            index++;
        }
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
