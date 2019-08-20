package view.javafx.menu;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import util.Lambda;
import view.interfaces.GameIntroView;
import view.javafx.ViewGetterUtil;

/**
 * Implementation of GameIntroView for javafx.
 */
public class GameIntroViewImpl implements GameIntroView {

    private static final int DIMENSIONSCREENTESTX = 1920;
    private static final int DIMENSIONSCREENTESTY = 1030;
    private static final int SCALEMULTIPLIER = 3;

    private static final String NAMEPANE = "pnIntro1";
    private final FadeTransition fd;
    private final Pane pnMain;
    private final int defaultX;
    private final int defaultY;
    private final Lambda l;

    /**
     * Create a {@link GameIntroViewImpl}.
     * @param msMenu Time for the fade effects. 
     * @param l operation called when the fade effect end.
     */
    public GameIntroViewImpl(final long msMenu, final Lambda l) {
        final Scene scene = ViewGetterUtil.getScene();
        this.l = l;
        this.pnMain = ViewGetterUtil.getNodeByName("pnIntro", Pane.class);
        this.defaultX = (int) scene.getWidth();
        this.defaultY = (int) scene.getHeight();
        fd = new FadeTransition(Duration.millis(msMenu), pnMain);
        setBind(ViewGetterUtil.getNodeByName(NAMEPANE, Pane.class), scene);
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateBind(ViewGetterUtil.getNodeByName(NAMEPANE, Pane.class), scene);
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            updateBind(ViewGetterUtil.getNodeByName(NAMEPANE, Pane.class), scene);
        });
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void changedFather(final boolean ok) {
        if (ok) {
            fd.setToValue(1);
            fd.playFromStart();
        } else {
            fd.setToValue(0);
            fd.playFromStart();
            fd.setOnFinished(e -> l.use());
        }
    }

    private void setBind(final Pane p, final Scene s) {
        p.layoutXProperty().bind(s.widthProperty().divide(2).subtract(p.widthProperty().divide(2)));
        p.layoutYProperty().bind(s.heightProperty().divide(2).subtract(p.heightProperty().divide(2)));
        p.scaleYProperty().bind(p.scaleXProperty());
    }
    private void updateBind(final Pane p, final Scene s) {
        if (pnMain.getWidth() / defaultX > pnMain.getHeight() / defaultY) {
            p.scaleXProperty().bind(s.widthProperty().multiply(SCALEMULTIPLIER).divide(DIMENSIONSCREENTESTX));
        } else {
            p.scaleXProperty().bind(s.heightProperty().multiply(SCALEMULTIPLIER).divide(DIMENSIONSCREENTESTY));
        }
    }
}
