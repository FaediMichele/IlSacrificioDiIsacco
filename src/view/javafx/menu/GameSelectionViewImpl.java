package view.javafx.menu;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import controller.menu.SubMenu;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.interfaces.GameSelectionView;
import view.javafx.ViewGetterUtil;

/**
 * The implementation of view of the Game sub menu selection.
 */
public class GameSelectionViewImpl implements GameSelectionView {
    private static final String GAMEPANE = "pnGameRun";
    private static final String OPTIONSPANE = "pnInGameMenu";
    private final int defaultX;
    private final int defaultY;
    private final Pane pnMain;
    private final FadeTransition optionsTransition;
    private final Pane optionsPane;
    private final FadeTransition fd;

    /**
     * Create a new GameSelectionViewImpl.
     * @param msMenu time for the animation of the sliding context menu.
     */
    public GameSelectionViewImpl(final long msMenu) {
        final Scene scene = ViewGetterUtil.getScene();
        this.defaultX = (int) scene.getWidth();
        this.defaultY = (int) scene.getHeight();
        this.pnMain = ViewGetterUtil.getNodeByName("pnGame", Pane.class);
        pnMain.setOpacity(0);
        fd = new FadeTransition(Duration.millis(msMenu), pnMain);
        optionsPane = ViewGetterUtil.getNodeByName(OPTIONSPANE, Pane.class);
        optionsTransition = new FadeTransition(fd.getDuration(), optionsPane);
        initOptionsPane();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBind(final Set<Object> paneOfSubMenu) {
        final Scene scene = ViewGetterUtil.getScene();
        this.bindDown(ViewGetterUtil.getNodeByName(GAMEPANE, Pane.class), optionsPane);
        final Supplier<Stream<Pane>> str = () -> Stream.of(paneOfSubMenu.toArray()).map(o -> Pane.class.cast(o));
        str.get().forEach(p -> setBind(p, scene));
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            str.get().forEach(p -> updateBind(p, scene));
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            str.get().forEach(p -> updateBind(p, scene));
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectSubMenu(final SubMenu end) {
        optionsTransition.setFromValue(0.0);
        optionsTransition.setToValue(1.0);
        optionsTransition.play();
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
            fd.setToValue(1);
            fd.playFromStart();
            fd.setOnFinished((e) -> System.out.println("Il gioco parte (sono in gameSubMenuSelectionView)"));
        }
    }

    private void setBind(final Pane p, final Scene s) {
        p.layoutXProperty().bind(s.widthProperty().divide(2).subtract(p.widthProperty().divide(2)).
                add(s.widthProperty().divide(defaultX).multiply(p.getLayoutX())));
        p.layoutYProperty().bind(s.heightProperty().divide(2).subtract(p.heightProperty().divide(2)).
                add(s.heightProperty().divide(defaultY).multiply(p.getLayoutY())));
        p.scaleYProperty().bind(p.scaleXProperty());
    }

    private void bindDown(final Pane from, final Pane dest) {
        dest.layoutYProperty().bind(from.layoutYProperty().add(from.heightProperty().multiply(from.scaleYProperty())));
    }

    /**
     * Update the scale based on the edge with the minus ratio.
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

    private void initOptionsPane() {
        final double scale = 9 / 10;
        optionsPane.setPrefWidth(pnMain.getPrefWidth() * scale);
        optionsPane.setPrefHeight(pnMain.getPrefHeight() * scale);

        optionsPane.layoutXProperty().bind(pnMain.widthProperty().subtract(optionsPane.widthProperty()).divide(2));
        optionsPane.layoutYProperty().bind(pnMain.heightProperty().subtract(optionsPane.heightProperty()).divide(2));
    }
}
