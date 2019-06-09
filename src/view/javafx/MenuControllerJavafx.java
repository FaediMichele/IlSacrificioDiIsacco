package view.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.util.Duration;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import util.Pair;
import view.CharacterInfo;
import view.ConfigurationManager;
import view.SubMenuSelection;
import view.node.CircleList;

/**
 * This is the class that handle the main menu of javafx.
 *
 */
public class MenuControllerJavafx {

    //CircleList. Height is calculated to have the same height and width even with the resize of the window.
    private static final int CL_WIDTH = 150;
    private static final double CL_SCALE = 1;
    private static final long CL_TIME = 300;
    private static final int CL_X = 125;
    private static final int CL_Y = 25;
    // Character image
    private static final double CR_WIDTH = 40;
    private static final double CR_HEIGHT = 40;


    private final SubMenuSelection menu = new SubMenuSelectionJavafx(500);
    private final ConfigurationManager manager = new ConfigurationManagerJavafx("/config.ini");

    @FXML private ImageView imgNameOfGame;
    @FXML private ImageView imgIsaac;
    @FXML private Pane pnMain;
    @FXML private Pane pnEnter;
    @FXML private Pane pnRun;
    @FXML private Pane pnGame;
    @FXML private ImageView imgNewRun;

    @FXML private ImageView imgOptions;
    @FXML private ImageView imgSelector;

    @FXML private ProgressBar prgSpeed;
    @FXML private ProgressBar prgDamage;
    @FXML private ProgressBar prgLife;
    @FXML private ImageView imgRandom;
    @FXML private ImageView imgName;
    @FXML private ImageView imgHeart;
    @FXML private ImageView imgSpeed;
    @FXML private ImageView imgDamage;


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
            if (manager.getKeyMap().containsKey(k.getCode())) {
                menu.get().input(manager.getKeyMap().get(k.getCode()));
            }
        });
        setImageView(null, imgRandom);
        final CircleList list = new CircleListRandomJavafx(CL_WIDTH, CL_WIDTH / pnMain.getWidth() * pnMain.getHeight(),
               CL_SCALE, Duration.millis(CL_TIME), imgRandom, CL_TIME);
        list.setMarginLeft(CL_X);
        list.setMarginTop(CL_Y);
        pnRun.getChildren().add((Node) list);
        Platform.runLater(() -> pnMain.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateSize(pnMain, new Rectangle2D(0d, 0d, (double) oldVal,
                    pnMain.getHeight()), new Rectangle2D(0d, 0d, (double) newVal, pnMain.getHeight()));

            // Set the correct position for the sub menu.
            menu.goTo(menu.get(), menu.get());
        }));
        Platform.runLater(() -> pnMain.heightProperty().addListener((obs, oldVal, newVal) -> {
            updateSize(pnMain, new Rectangle2D(0d, 0d, pnMain.getWidth(),
                    (double) oldVal), new Rectangle2D(0d, 0d, pnMain.getWidth(), (double) newVal));

            // Set the correct position for the sub menu.
            menu.goTo(menu.get(), menu.get());
        }));
        pnMain.requestFocus();

        menu.add(new SubMenuEnter(menu, pnEnter, imgNameOfGame, imgIsaac));
        menu.add(new SubMenuGame(menu, pnGame, imgNewRun, imgOptions, imgSelector));
        menu.add(new SubMenuRun(menu, pnRun, prgLife, prgDamage, prgSpeed, imgName, imgRandom,
                imgHeart, imgSpeed, imgDamage, list, getCharacterMap()));
    }

    private List<Pair<ImageView, CharacterInfo>> getCharacterMap() {
        final Set<CharacterInfo> cfs = manager.getCharactes();
        final List<Pair<ImageView, CharacterInfo>> ret = new ArrayList<>(cfs.size());
        cfs.forEach(ci -> {
            final ImageView imgv = setImageView((Image) ci.getImage(), new ImageView());
            pnRun.getChildren().add(imgv);
            ret.add(new Pair<ImageView, CharacterInfo>(imgv, ci));
        });
        return ret;
    }

    private ImageView setImageView(final Image img, final ImageView base) {
        base.setFitHeight(CR_HEIGHT);
        base.setFitWidth(CR_WIDTH);
        base.setImage((Image) img); 
        base.setPreserveRatio(true);
        base.setLayoutX(0);
        base.setLayoutY(0);
        return base;
    }

    private void updateSize(final Parent p, final Rectangle2D oldValue, final Rectangle2D newValue) {
        p.getChildrenUnmodifiable().forEach(n -> {
            if (n instanceof Parent) {
                updateSize((Parent) n, oldValue, newValue);
            }
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
            } else if (n instanceof CircleList) {
                final CircleList list = (CircleList) n;
                list.setWidth(list.getWidth() * newValue.getWidth()
                        / oldValue.getWidth());
                list.setHeight(list.getHeight() * newValue.getHeight()
                        / oldValue.getHeight());
            }
        });
    }
}
