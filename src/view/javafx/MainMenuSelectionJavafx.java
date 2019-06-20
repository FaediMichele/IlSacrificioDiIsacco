package view.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import util.Pair;
import view.CharacterInfo;
import view.ConfigurationManager;
import view.SubMenu;
import view.SubMenuSelection;
import view.node.TranslationPages;

/**
 * The selector for JavaFx.
 * It manage the animation for change the sub menu.
 */
public class MainMenuSelectionJavafx extends SubMenuSelection {
    private static final int DEFAULT_X = 640;
    private static final int DEFAULT_Y = 344;
    private static final String SHADOWPANE = "pnShadow";
    private static final String RUNPANE = "pnRun";
    private static final String ENTER = "pnEnter";
    private static final String GAME = "pnGame";

    // Character image
    private static final double CR_WIDTH = 40;
    private static final double CR_HEIGHT = 40;

    private final TranslationPages tp;
    private final FadeTransition fd;
    private final Pane pnMain; 
    private final ConfigurationManager manager;

    /**
     * Create the {@link MainMenuSelectionJavafx}. 
     * @param main the main pane that contains all sub menu node.
     * @param s the scene of the application.
     * @param msPage the time for the animation for slide the sub Menu.
     * @param msMenu the time for the fade effect.
     * @param manager for the character selection images and values.
     */
    public MainMenuSelectionJavafx(final Pane main, final Scene s, final long msPage, final long msMenu, final ConfigurationManager manager) {
        super();
        this.pnMain = main;
        tp = new TranslationPageJavafx(main, s, msPage);
        fd = new FadeTransition(Duration.millis(msMenu), main);
        this.manager = manager;
        init(s);
    }

    private void init(final Scene s) {
        initShadow(s);
        setImageView(null, (ImageView) getByName(s, "imgRandom"));

        // Add the sub menu in the sub menu handler.
        add(new SubMenuEnter(this, (Pane) getByName(s, ENTER), (ImageView) getByName(s, "imgNameOfGame"),
                (ImageView) getByName(s, "imgIsaac"), (ImageView) getByName(s, "imgBackgroundEnter")));
        add(new SubMenuGame(this, (Pane) getByName(s, GAME), (ImageView) getByName(s, "imgNewRun"),
                (ImageView) getByName(s, "imgOptions"), (ImageView) getByName(s, "imgSelector")));
        add(new SubMenuRun(this, (Pane) getByName(s, RUNPANE), (ProgressBar) getByName(s, "prgLife"),
                (ProgressBar) getByName(s, "prgDamage"), (ProgressBar) getByName(s, "prgSpeed"),
                (ImageView) getByName(s, "imgName"), (ImageView) getByName(s, "imgRandom"),
                (ImageView) getByName(s, "imgHeart"), (ImageView) getByName(s, "imgSpeed"),
                (ImageView) getByName(s, "imgDamage"), getCharacterMap(s)));

        // initialize the layout (x, y) of the pane in the menu.
        asSet().stream().map(sm -> (Pane) sm.getMain()).forEach(p -> setBind(p, s));
        this.bindDown((Pane) getByName(s, ENTER), (Pane) getByName(s, GAME));
        this.bindLeft((Pane) getByName(s, ENTER), (Pane) getByName(s, GAME));
        this.bindDown((Pane) getByName(s, ENTER), (Pane) getByName(s, RUNPANE));
        this.bindRight((Pane) getByName(s, GAME), (Pane) getByName(s, RUNPANE));

        // When the window change the size all pane must be resize as well.
        s.widthProperty().addListener((obs, oldVal, newVal) -> {
            asSet().stream().map(sm -> (Pane) sm.getMain()).forEach(p -> updateBind(p, s));
            jumpTo(get());
        });
        s.heightProperty().addListener((obs, oldVal, newVal) -> {
            asSet().stream().map(sm -> (Pane) sm.getMain()).forEach(p -> updateBind(p, s));
            jumpTo(get());
        });
    }

    /**
     * Set the binding for the shadow pane.
     * @param s
     */
    private void initShadow(final Scene s) {
        getByName(s, SHADOWPANE).translateXProperty().bind(pnMain.translateXProperty().multiply(-1));
        getByName(s, SHADOWPANE).translateYProperty().bind(pnMain.translateYProperty().multiply(-1));
        ((Pane) getByName(s, SHADOWPANE)).prefWidthProperty().bind(s.widthProperty());
        ((Pane) getByName(s, SHADOWPANE)).prefHeightProperty().bind(s.heightProperty());
        ((ImageView) getByName(s, "imgShadow")).fitWidthProperty().bind(((Pane) getByName(s, SHADOWPANE)).widthProperty());
        ((ImageView) getByName(s, "imgShadow1")).fitWidthProperty().bind(((Pane) getByName(s, SHADOWPANE)).widthProperty());
        ((ImageView) getByName(s, "imgShadow")).fitHeightProperty().bind(((Pane) getByName(s, SHADOWPANE)).heightProperty());
        ((ImageView) getByName(s, "imgShadow1")).fitHeightProperty().bind(((Pane) getByName(s, SHADOWPANE)).heightProperty());
    }

    /**
     * Get the character map. The map is not required because the acces is lienar.
     * @return
     */
    private List<Pair<ImageView, CharacterInfo>> getCharacterMap(final Scene s) {
        final Set<CharacterInfo> cfs = manager.getCharactes();
        final List<Pair<ImageView, CharacterInfo>> ret = new ArrayList<>(cfs.size());
        cfs.forEach(ci -> {
            final ImageView imgv = setImageView((Image) ci.getImage(), new ImageView());
            ((Pane) getByName(s, RUNPANE)).getChildren().add(imgv);
            ret.add(new Pair<ImageView, CharacterInfo>(imgv, ci));
        });
        return ret;
    }

    /**
     * Initialize an image for the circle list.
     * @param img
     * @param base
     * @return
     */
    private ImageView setImageView(final Image img, final ImageView base) {
        base.setFitHeight(CR_HEIGHT);
        base.setFitWidth(CR_WIDTH);
        base.setImage((Image) img); 
        base.setPreserveRatio(true);
        base.setLayoutX(0);
        base.setLayoutY(0);
        return base;
    }

    /**
     * Update the scale based on the edge with the minus ratio.
     * @param p
     * @param s
     */
    private void updateBind(final Pane p, final Scene s) {
        if (pnMain.getWidth() / DEFAULT_X > pnMain.getHeight() / DEFAULT_Y) {
            p.scaleXProperty().bind(s.heightProperty().divide(DEFAULT_Y));
        } else {
            p.scaleXProperty().bind(s.widthProperty().divide(DEFAULT_X));
        }
    }

    /**
     * Initialize the layout binding.
     * @param p
     * @param s
     */
    private void setBind(final Pane p, final Scene s) {
        // Challenge: try to discover how it work :-)
        p.layoutXProperty().bind(s.widthProperty().divide(2).subtract(p.widthProperty().divide(2)).
                add(s.widthProperty().divide(DEFAULT_X).multiply(p.getLayoutX())));
        p.layoutYProperty().bind(s.heightProperty().divide(2).subtract(p.heightProperty().divide(2)).
                add(s.heightProperty().divide(DEFAULT_Y).multiply(p.getLayoutY())));
        p.scaleYProperty().bind(p.scaleXProperty());
    }
    private void bindDown(final Pane from, final Pane dest) {
        dest.layoutYProperty().bind(from.layoutYProperty().add(from.heightProperty().multiply(from.scaleYProperty())));
    }
    private void bindRight(final Pane from, final Pane dest) {
        dest.layoutXProperty().bind(from.layoutXProperty().add(from.widthProperty().multiply(from.scaleXProperty())));
    }
    private void bindLeft(final Pane from, final Pane dest) {
        dest.layoutXProperty().bind(from.layoutXProperty());
    }
    private Node getByName(final Scene s, final String name) {
        return s.lookup("#" + name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goTo(final SubMenu start, final SubMenu end) {
        Objects.requireNonNull(end);
        if (start != null && !tp.contains(start.getMain())) {
            tp.addPage(start.getMain());
        }
        if (!tp.contains(end.getMain())) {
            tp.addPage(end.getMain());
        }
        tp.goTo(end.getMain());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void jumpTo(final SubMenu dest) {
        Objects.requireNonNull(dest);
        if (!tp.contains(dest.getMain())) {
            tp.addPage(dest.getMain());
        }
        tp.jumpTo(dest.getMain());
    }

    /**
     * Play a fade animation.
     */
    @Override
    public void selectMenu(final SubMenuSelection previous, final SubMenuSelection dest) {
        if (previous.equals(this)) {
            fd.setToValue(0);
            fd.playFromStart();
        } else {
            (new Thread() { public void run() {
                    try {
                        Thread.sleep((long) fd.getTotalDuration().toMillis());
                        Platform.runLater(() -> {
                            fd.setToValue(1);
                            fd.playFromStart();
                        });
                    } catch (InterruptedException e) {
                            e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
