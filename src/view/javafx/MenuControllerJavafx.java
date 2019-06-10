package view.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
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
    private static final int DEFAULT_X = 640;
    private static final int DEFAULT_Y = 344;
    private static final int TIME_SUBMENU = 250;

    //CircleList. Height is calculated to have the same height and width even with the resize of the window.
    private static final int CL_WIDTH = 150;
    private static final double CL_SCALE = 0.5;
    private static final long CL_TIME = 300;
    private static final int CL_X = 135;
    private static final int CL_Y = 50;
    // Character image
    private static final double CR_WIDTH = 40;
    private static final double CR_HEIGHT = 40;


    private SubMenuSelection menu;
    private final ConfigurationManager manager = new ConfigurationManagerJavafx("/config.ini");

    @FXML private ImageView imgNameOfGame;
    @FXML private ImageView imgIsaac;
    @FXML private Pane pnMain;
    @FXML private Pane pnShadow;
    @FXML private Pane pnEnter;
    @FXML private Pane pnRun;
    @FXML private Pane pnGame;
    @FXML private ImageView imgNewRun;
    @FXML private ImageView imgBackgroundEnter;

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
    @FXML private ImageView imgShadow;
    @FXML private ImageView imgShadow1;


    /**
     * Initialize the menu.
     * @param s the scene. It is used for the input.
     */
    public void start(final Scene s) {
        menu = new SubMenuSelectionJavafx(pnMain, s, TIME_SUBMENU);
        initShadow(s);
        //s.widthProperty().addListener(b -> System.out.println(s.getWidth() + " " + pnShadow.getWidth() + " " + pnShadow.getBoundsInParent().getWidth()));
        //s.heightProperty().addListener((b) -> pnShadow.setPrefHeight(s.getHeight()));
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

        menu.add(new SubMenuEnter(menu, pnEnter, imgNameOfGame, imgIsaac, imgBackgroundEnter));
        menu.add(new SubMenuGame(menu, pnGame, imgNewRun, imgOptions, imgSelector));
        menu.add(new SubMenuRun(menu, pnRun, prgLife, prgDamage, prgSpeed, imgName, imgRandom,
                imgHeart, imgSpeed, imgDamage, list, getCharacterMap()));

        pnMain.getChildrenUnmodifiable().stream().filter(n -> (n instanceof Pane)).filter(n -> !n.equals(pnShadow))
            .map(n -> (Pane) n).forEach(p -> setBind(p, s));
        s.widthProperty().addListener((obs, oldVal, newVal) -> {
            pnMain.getChildrenUnmodifiable().stream().filter(n -> (n instanceof Pane)).filter(n -> !n.equals(pnShadow))
            .map(n -> (Pane) n).forEach(p -> updateBind(p, s));
            menu.jumpTo(menu.get());
        });
        s.heightProperty().addListener((obs, oldVal, newVal) -> {
            pnMain.getChildrenUnmodifiable().stream().filter(n -> (n instanceof Pane)).filter(n -> !n.equals(pnShadow))
            .map(n -> (Pane) n).forEach(p -> updateBind(p, s));
            menu.jumpTo(menu.get());
        });
        pnMain.requestFocus();
    }

    private void initShadow(final Scene s) {
        pnShadow.translateXProperty().bind(pnMain.translateXProperty().multiply(-1));
        pnShadow.translateYProperty().bind(pnMain.translateYProperty().multiply(-1));
        pnShadow.prefWidthProperty().bind(s.widthProperty());
        pnShadow.prefHeightProperty().bind(s.heightProperty());
        imgShadow.fitWidthProperty().bind(pnShadow.widthProperty());
        imgShadow1.fitWidthProperty().bind(pnShadow.widthProperty());
        imgShadow.fitHeightProperty().bind(pnShadow.heightProperty());
        imgShadow1.fitHeightProperty().bind(pnShadow.heightProperty());
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

    private void updateBind(final Pane p, final Scene s) {
        if (pnMain.getWidth() / DEFAULT_X > pnMain.getHeight() / DEFAULT_Y) {
            p.scaleXProperty().bind(s.heightProperty().divide(DEFAULT_Y));
        } else {
            p.scaleXProperty().bind(s.widthProperty().divide(DEFAULT_X));
        }
    }

    private void setBind(final Pane p, final Scene s) {
        // make a challenge: try to discover how it work :-)
        p.layoutXProperty().bind(s.widthProperty().divide(2).subtract(p.widthProperty().divide(2)).
                add(s.widthProperty().divide(DEFAULT_X).multiply(p.getLayoutX())));
        p.layoutYProperty().bind(s.heightProperty().divide(2).subtract(p.heightProperty().divide(2)).
                add(s.heightProperty().divide(DEFAULT_Y).multiply(p.getLayoutY())));
        p.scaleYProperty().bind(p.scaleXProperty());
    }
}

