package view.javafx.menu;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import controller.menu.SubMenu;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import util.Lambda;
import view.Sound;
import view.TypeOfAudio;
import view.interfaces.GameSelectionView;
import view.javafx.ContextPageJavafx;
import view.javafx.SoundJavafx;
import view.javafx.ViewGetterUtil;
import view.node.TranslationPages;

/**
 * The implementation of view of the Game sub menu selection.
 */
public class GameSelectionViewImpl implements GameSelectionView {
    private static final int DIMENSIONSCREENTESTX = 1920;
    private static final int DIMENSIONSCREENTESTY = 1030;
    private static final int SCALEMULTIPLIER = 3;

    private static final String GAMEPANE = "pnGameRun";
    private static final String OPTIONSPANE = "pnInGameMenu";
    private static final String LOOSEPANE = "pnGameLoose";
    private static final long MSSUBMENU = 250;
    private final Sound characterSelected = new SoundJavafx("/menuSound/characterSelected.wav", TypeOfAudio.EFFECT);
    private final int defaultX;
    private final int defaultY;
    private final Pane pnMain;
    private final FadeTransition fd;
    private final TranslationPages tp;
    private Lambda onIntroEnded;

    /**
     * Create a new GameSelectionViewImpl.
     * @param msMenu time for the animation of the sliding context menu.
     */
    public GameSelectionViewImpl(final long msMenu) {
        final Scene scene = ViewGetterUtil.getScene();
        this.defaultX = ((int) scene.getWidth());
        this.defaultY = ((int) scene.getHeight());
        this.pnMain = ViewGetterUtil.getNodeByName("pnGame", Pane.class);
        pnMain.setOpacity(0);
        fd = new FadeTransition(Duration.millis(msMenu), pnMain);
        tp = new ContextPageJavafx(ViewGetterUtil.getNodeByName(GAMEPANE, Pane.class), MSSUBMENU);
        tp.addPage(ViewGetterUtil.getNodeByName(OPTIONSPANE, Pane.class),
                ViewGetterUtil.getNodeByName(LOOSEPANE, Pane.class));
        characterSelected.setEndListener(() -> {
            fd.setToValue(1);
            fd.playFromStart();
            if (onIntroEnded != null) {
                onIntroEnded.use();
            }
        });
   }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBind(final Set<Object> paneOfSubMenu) {
        final Scene scene = ViewGetterUtil.getScene();
        final Supplier<Stream<Pane>> str = () -> Stream.of(paneOfSubMenu.toArray()).map(o -> Pane.class.cast(o));
        str.get().forEach(p -> setBind(p, scene));
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            str.get().forEach(p -> updateBind(p, scene));
            tp.jumpTo(tp.getSelected());
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            str.get().forEach(p -> updateBind(p, scene));
            tp.jumpTo(tp.getSelected());
        });
        tp.addPage(paneOfSubMenu.toArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectSubMenu(final SubMenu end) {
        tp.goTo(end.getSubMenuView().getMain());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeSelector(final boolean previous) {
        if (previous) {
            fd.setToValue(0);
            fd.playFromStart();
        } else {
            characterSelected.play();
            System.out.println("PLAYING");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayingIntro() {
        return characterSelected.isPlaying();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnIntroEnded(final Lambda l) {
        onIntroEnded = l;
    }

    private void setBind(final Pane p, final Scene s) {
        p.layoutXProperty().bind(s.widthProperty().divide(2).subtract(p.widthProperty().divide(2)).
                add(s.widthProperty().divide(defaultX).multiply(p.getLayoutX())));
        p.layoutYProperty().bind(s.heightProperty().divide(2).subtract(p.heightProperty().divide(2)).
                add(s.heightProperty().divide(defaultY).multiply(p.getLayoutY())));
        p.scaleYProperty().bind(p.scaleXProperty());
    }


    /**
     * Update the scale based on the edge with the minus ratio.
     * @param p
     * @param s
     */
    private void updateBind(final Pane p, final Scene s) {
        if (pnMain.getWidth() / defaultX > pnMain.getHeight() / defaultY) {
            p.scaleXProperty().bind(s.heightProperty().multiply(SCALEMULTIPLIER).divide(DIMENSIONSCREENTESTY));
        } else {
            p.scaleXProperty().bind(s.widthProperty().multiply(SCALEMULTIPLIER).divide(DIMENSIONSCREENTESTX));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeFullScreen() {
        ViewGetterUtil.switchFullScreen();
    }
}
