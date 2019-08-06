package view.javafx.game.menu;

import java.io.IOException;
import java.util.Objects;

import controller.menu.MenuSelection;
import controller.menu.SubMenu;
import controller.menu.SubMenuSelection;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 */
public class GameSubMenuSelection extends SubMenuSelection {

    private static final String GAMEPANE = "pnGameRun";
    private static final String OPTIONSPANE = "pnGameMenu";
    private static final String GAMECANVAS = "cnvGame";

    private final int defaultX;
    private final int defaultY;
    private final Pane pnMain;
    private final FadeTransition optionsTransition;
    private final Pane optionsPane;
    private final FadeTransition fd;

    /**
     * 
     * Create the {@link MainMenuSelectionView}. 
     * @param parent the {@link MenuSelection}.
     * @param main the main pane that contains all sub menu node.
     * @param scene the scene of the application.
     * @param msMenu the time for the fade effect.
     */
    public GameSubMenuSelection(final MenuSelection parent, final Pane main, final Scene scene, final long msMenu) {
        super(parent);
        this.defaultX = (int) scene.getWidth();
        this.defaultY = (int) scene.getHeight();
        this.pnMain = main;
        pnMain.setOpacity(0);
        fd = new FadeTransition(Duration.millis(msMenu), pnMain);
        optionsPane = (Pane) getByName(scene, OPTIONSPANE);
        optionsTransition = new FadeTransition(fd.getDuration(), optionsPane);
        initOptionsPane();

        try {
            add(new SubMenuGame(this, (Pane) getByName(scene, GAMEPANE), (Canvas) getByName(scene, GAMECANVAS)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(new SubMenuOption(this));

        asSet().stream().map(sm -> (Pane) sm.getMain()).forEach(p -> setBind(p, scene));
        this.bindDown((Pane) getByName(scene, GAMEPANE), optionsPane);

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            asSet().stream().map(sm -> (Pane) sm.getMain()).forEach(p -> updateBind(p, scene));
            jumpTo(get());
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            asSet().stream().map(sm -> (Pane) sm.getMain()).forEach(p -> updateBind(p, scene));
            jumpTo(get());
        });
    }

    private void initOptionsPane() {
        final double scale = 9 / 10;
        optionsPane.setPrefWidth(pnMain.getPrefWidth() * scale);
        optionsPane.setPrefHeight(pnMain.getPrefHeight() * scale);

        optionsPane.layoutXProperty().bind(pnMain.widthProperty().subtract(optionsPane.widthProperty()).divide(2));
        optionsPane.layoutYProperty().bind(pnMain.heightProperty().subtract(optionsPane.heightProperty()).divide(2));
    }

    /**
     * Initialize the layout binding.
     * @param p
     * @param s
     */
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

    @Override
    public final long getTimeAnimation() {
        return (long) optionsTransition.getDuration().toMillis();
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void selectSubMenu(final SubMenu start, final SubMenu end) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        optionsTransition.setFromValue(0.0);
        optionsTransition.setToValue(1.0);
        optionsTransition.play();
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void jumpTo(final SubMenu dest) {
        Objects.requireNonNull(dest);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void selectMenu(final SubMenuSelection previous, final SubMenuSelection dest) {
        if (previous.equals(this)) {
            fd.setToValue(0);
            fd.playFromStart();
        }
    }

    /**
     * Start the animation of the fade transition.
     */
    public void startAnimationSelected() {
        fd.setToValue(1);
        fd.playFromStart();
        fd.setOnFinished((e) -> System.out.println("Il gioco parte (sono in gameSubMenuSelection)"));
    }

}
