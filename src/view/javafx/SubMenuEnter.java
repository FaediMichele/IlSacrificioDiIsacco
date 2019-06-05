package view.javafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import view.AnimatedView;
import view.AnimatedViewJavafx;
import view.SubMenu;
import view.SubMenuSelection;
import view.TimedViews;
import view.TimedViewsJavafx;

/**
 * This class is the sub menu for the entering in the game.
 */
public class SubMenuEnter extends SubMenu {
    private static final String NAMEOFGAME = "/menuImgs/nameOfGame-";
    private static final String ISAAC = "/menuImgs/isaac-";
    private static final String FORMAT = ".png";
    private static final long FRAMETIME_ISAAC = 300;
    private static final long FRAMETIME_NAMEOFGAME = 150;

    private final TimedViews timeNameOfGame = new TimedViewsJavafx();
    private final TimedViews timeIsaac = new TimedViewsJavafx();


    /**
     * Create the entering menu.
     * @param selector the selector.
     * @param pnMain the {@link Pane} that contains the other @param.
     * @param nameOfGame the {@link ImageView} for the name of the game.
     * @param isaac the {@link ImageView} for crying isaac.
     */
    public SubMenuEnter(final SubMenuSelection selector, final Pane pnMain, final ImageView nameOfGame, final ImageView isaac) {
        super(selector, pnMain);
        final AnimatedView nameOfGameAnimated = new AnimatedViewJavafx(nameOfGame);
        final AnimatedView isaacAnimated = new AnimatedViewJavafx(isaac);

        setFrames(nameOfGameAnimated, NAMEOFGAME, FORMAT);
        setFrames(isaacAnimated, ISAAC, FORMAT);
        timeIsaac.add(isaacAnimated);
        timeIsaac.setMilliseconds(FRAMETIME_ISAAC);
        timeNameOfGame.add(nameOfGameAnimated);
        timeNameOfGame.setMilliseconds(FRAMETIME_NAMEOFGAME);
        timeIsaac.start();
        timeNameOfGame.start();
    }

    @Override
    public final void select() {
        timeIsaac.start();
        timeNameOfGame.start();
    }

    @Override
    public final void enter() {
        if (getSelector().contains(SubMenuGame.class)) {
            getSelector().select(SubMenuGame.class);
        }
    }

    @Override
    public final void reset() {
        timeIsaac.stop();
        timeNameOfGame.stop();
    }

    /**
     * Set the animated view with a initial name and a format.
     * @param av the {@link AnimatedView}.
     * @param initialName the name of the animation (es animation-)
     * @param format the format of all the file (es .png).
     */
    private void setFrames(final AnimatedView av, final String initialName, final String format) {
        int index = 0;
        while (getClass().getResource(initialName + index + format) != null) {
            av.setFrames(new Image(initialName + index + format));
            index++;
        }
    }
}
