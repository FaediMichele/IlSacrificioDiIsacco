package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;

/**
 * This is the class that handle the main menu of javafx.
 *
 */
public class MenuController {
    private static final String BACKGROUND = "/menuImgs/background.jpg";
    private static final String NAMEOFGAME = "/menuImgs/nameOfGame-";
    private static final String PRESSSTART = "/menuImgs/pressStart-";
    //private static final String SELECTOR = "/menuImgs/selector-";
    private static final String FORMAT = ".png";
    private static final long FRAMETIME_DEFAULT = 300;
    private static final long FRAMETIME_NAMEOFGAME = 150;

    private final TimedViews timeDefault = new TimedViewsJavafx();
    private final TimedViews timeNameOfGame = new TimedViewsJavafx();

    @FXML private ImageView imgNameOfGame;
    @FXML private ImageView imgPressStart;
    @FXML private Pane pnMain;
    /*
    @FXML private ImageView imgHeart;
    @FXML private ImageView imgSpeed;
    @FXML private ImageView imgDamage;
    @FXML private ImageView imgNewRun;
    @FXML private ImageView imgOptions;
    @FXML private ImageView imgSelector;
    */

    /**
     * Initialize the menu.
     */
    public void start() {
        setTimedViews();
        pnMain.setBackground(new Background(new BackgroundImage(
                new Image(BACKGROUND), null, null, null, null)));
    }

    private void setTimedViews() {
        final AnimatedView nameOfGame = new AnimatedViewJavafx(imgNameOfGame);
        final AnimatedView pressStart = new AnimatedViewJavafx(imgPressStart);
        //final AnimatedView selector = new AnimatedViewJavafx(imgSelector);

        setFrames(nameOfGame, NAMEOFGAME);
        setFrames(pressStart, PRESSSTART);
        //setFrames(selector, SELECTOR);
        timeDefault.add(pressStart); // , selector);
        timeDefault.setMilliseconds(FRAMETIME_DEFAULT);
        timeNameOfGame.add(nameOfGame);
        timeNameOfGame.setMilliseconds(FRAMETIME_NAMEOFGAME);
        timeDefault.start();
        timeNameOfGame.start();
    }

    private void setFrames(final AnimatedView av, final String initialName) {
        int index = 0;
        while (getClass().getResource(initialName + index + FORMAT) != null) {
            class Run implements Runnable {
                private int val;
                Run(final int i) {
                    val = i;
                }
                @Override
                public void run() {
                    av.setFrames(new Image(initialName + val + FORMAT));
                }
            }
            Platform.runLater(new Run(index));
            index++;
        }
    }
}
