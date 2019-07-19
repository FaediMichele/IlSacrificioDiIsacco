package view.javafx.game.menu;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.MenuSelection;
import view.SubMenu;
import view.SubMenuSelection;

/**
 *
 */
public class GameSubMenuSelection extends SubMenuSelection {

    private static final String GAMEPANE = "pnGameRun";
    private static final String OPTIONSPANE = "pnGameMenu";

    private final int defaultX;
    private final int defaultY;
    private final Pane pnMain;
    private final FadeTransition fd;

    /**
     * 
     * Create the {@link MainMenuSelectionJavafx}. 
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
        fd = new FadeTransition(Duration.millis(msMenu), main);

        add(new SubMenuGame(this, (Pane) getByName(scene, GAMEPANE)));
        add(new SubMenuOption(this, (Pane) getByName(scene, OPTIONSPANE)));

        asSet().stream().map(sm -> (Pane) sm.getMain()).forEach(p -> setBind(p, scene));
        this.bindDown((Pane) getByName(scene, GAMEPANE), (Pane) getByName(scene, OPTIONSPANE));

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            asSet().stream().map(sm -> (Pane) sm.getMain()).forEach(p -> updateBind(p, scene));
            jumpTo(get());
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            asSet().stream().map(sm -> (Pane) sm.getMain()).forEach(p -> updateBind(p, scene));
            jumpTo(get());
        });
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
        return (long) fd.getDuration().toMillis();
    }

    @Override
    public void goTo(final SubMenu start, final SubMenu end) {
        // TODO Auto-generated method stub -> due casi: appare options/scompare options
    }

    @Override
    public void jumpTo(final SubMenu dest) {
        // TODO Auto-generated method stub

    }

    @Override
    public void selectMenu(final SubMenuSelection previous, final SubMenuSelection dest) {
        // TODO Auto-generated method stub

    }

}
