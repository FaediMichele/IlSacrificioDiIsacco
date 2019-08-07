package view.javafx.game.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import util.Lambda;
import view.Sound;
import view.SubMenuView;
import view.javafx.SoundJavafx;
import view.javafx.TranslationPageJavafx;
import view.menuInterfaces.MainMenuSelectionView;
import view.node.TranslationPages;

/**
 * The selector for JavaFx. It manage the animation for change the sub menu.
 */
public class MainMenuSelectionViewImpl implements MainMenuSelectionView {
    private final List<Lambda> readyToChange = new ArrayList<>();

    private static final String SHADOWPANE = "pnShadow";
    private static final String RUNPANE = "pnRun";
    private static final String ENTER = "pnEnter";
    private static final String GAME = "pnMenuMain";

    private final int defaultX;
    private final int defaultY;
    // Character image
    private static final double CR_WIDTH = 40;
    private static final double CR_HEIGHT = 40;

    private final TranslationPages tp;
    private final FadeTransition fd;
    private final Pane pnMain;

    private final Sound changeSubMenuAudio = new SoundJavafx("/menuSound/pageTurn.wav");
    private final Sound backgroundAudioIntro = new SoundJavafx("/menuSound/audioIntro.wav");
    private final Sound backgroundAudio = new SoundJavafx("/menuSound/background.wav");
    private final Sound characterSelected = new SoundJavafx("/menuSound/characterSelected.wav");

    /**
     * Create the {@link MainMenuSelectionViewImpl}.
     * 
     * @param msPage the time for the animation for slide the sub Menu.
     * @param msMenu the time for the fade effect.
     */
    public MainMenuSelectionViewImpl(final long msPage, final long msMenu) {
        final Scene s = ViewGetter.getScene();
        this.defaultX = (int) s.getWidth();
        this.defaultY = (int) s.getHeight();
        pnMain = ViewGetter.getNodeByName("pnMainMenu", Pane.class);
        // pnMain.setOpacity(0);
        tp = new TranslationPageJavafx(pnMain, s, msPage);
        fd = new FadeTransition(Duration.millis(msMenu), pnMain);

        init(s);
        backgroundAudioIntro.setEndListener(() -> backgroundAudio.playInLoop());
        characterSelected.setEndListener(() -> readyToChange.forEach(Lambda::use));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playChanged() {
        changeSubMenuAudio.play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeSubMenu(final SubMenuView start, final SubMenuView end) {
        if (start != null && !tp.contains(start.getMain())) {
            tp.addPage(start.getMain());
        }
        if (end != null) {
            if (!tp.contains(end.getMain())) {
                tp.addPage(end.getMain());
            }
            tp.goTo(end.getMain());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void jumpTo(final SubMenuView dest) {
        Objects.requireNonNull(dest);
        if (!tp.contains(dest.getMain())) {
            tp.addPage(dest.getMain());
        }
        tp.jumpTo(dest.getMain());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playAudioCharacterSelected() {
        characterSelected.play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReadyToChangeListener(final Lambda l) {
        readyToChange.add(l);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeReadyToChangeListener(final Lambda l) {
        readyToChange.remove(l);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectSelection(final boolean previous) {
        if (previous) {
            fd.setToValue(0);
            fd.playFromStart();
            backgroundAudioIntro.stop();
            backgroundAudio.stop();
        } else {
            fd.setToValue(1);
            fd.playFromStart();
            backgroundAudioIntro.play();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBind(final List<Object> paneOfSubMenu) {
        final Scene s = ViewGetter.getScene();
        final Supplier<Stream<Pane>> str = () -> Stream.of(paneOfSubMenu.toArray()).map(o -> Pane.class.cast(o));
        str.get().forEach(p -> setBind(p, s));
        this.bindDown(ViewGetter.getNodeByName(ENTER, Pane.class),
                ViewGetter.getNodeByName(GAME, Pane.class));
        this.bindLeft(ViewGetter.getNodeByName(ENTER, Pane.class),
                ViewGetter.getNodeByName(GAME, Pane.class));
        this.bindDown(ViewGetter.getNodeByName(ENTER, Pane.class),
                ViewGetter.getNodeByName(RUNPANE, Pane.class));
        this.bindRight(ViewGetter.getNodeByName(GAME, Pane.class),
                ViewGetter.getNodeByName(RUNPANE, Pane.class));

        // When the window change the size all pane must be resize as well.
        s.widthProperty().addListener((obs, oldVal, newVal) -> {
            str.get().forEach(p -> updateBind(p, s));
        });
        s.heightProperty().addListener((obs, oldVal, newVal) -> {
            str.get().forEach(p -> updateBind(p, s));
        });
    }

    private void init(final Scene s) {
        initShadow(s);
        setImageView(null, ViewGetter.getNodeByName("imgRandom", ImageView.class));
    }

    private void initShadow(final Scene s) {
        ViewGetter.getNodeByName(SHADOWPANE, Pane.class).translateXProperty()
                .bind(pnMain.translateXProperty().multiply(-1));
        ViewGetter.getNodeByName(SHADOWPANE, Pane.class).translateYProperty()
                .bind(pnMain.translateYProperty().multiply(-1));
        ViewGetter.getNodeByName(SHADOWPANE, Pane.class).prefWidthProperty().bind(s.widthProperty());
        ViewGetter.getNodeByName(SHADOWPANE, Pane.class).prefHeightProperty().bind(s.heightProperty());
        ViewGetter.getNodeByName("imgShadow", ImageView.class).fitWidthProperty()
                .bind(ViewGetter.getNodeByName(SHADOWPANE, Pane.class).widthProperty());
        ViewGetter.getNodeByName("imgShadow1", ImageView.class).fitWidthProperty()
                .bind(ViewGetter.getNodeByName(SHADOWPANE, Pane.class).widthProperty());
        ViewGetter.getNodeByName("imgShadow", ImageView.class).fitHeightProperty()
                .bind(ViewGetter.getNodeByName(SHADOWPANE, Pane.class).heightProperty());
        ViewGetter.getNodeByName("imgShadow1", ImageView.class).fitHeightProperty()
                .bind(ViewGetter.getNodeByName(SHADOWPANE, Pane.class).heightProperty());
    }

    /**
     * Initialize an image for the circle list.
     * 
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
     * 
     * @param p
     * @param s
     */
    private void updateBind(final Pane p, final Scene s) {
        if (pnMain.getWidth() / defaultX > pnMain.getHeight() / defaultY) {
            p.scaleXProperty().bind(s.heightProperty().divide(defaultY));
        } else {
            p.scaleXProperty().bind(s.widthProperty().divide(defaultX));
        }
    }

    /**
     * Initialize the layout binding.
     * 
     * @param p
     * @param s
     */
    private void setBind(final Pane p, final Scene s) {
        // Challenge: try to discover how it work :-)
        p.layoutXProperty().bind(s.widthProperty().divide(2).subtract(p.widthProperty().divide(2))
                .add(s.widthProperty().divide(defaultX).multiply(p.getLayoutX())));
        p.layoutYProperty().bind(s.heightProperty().divide(2).subtract(p.heightProperty().divide(2))
                .add(s.heightProperty().divide(defaultY).multiply(p.getLayoutY())));
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

}
