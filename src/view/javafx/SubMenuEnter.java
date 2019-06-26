package view.javafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import view.AnimatedView;
import view.Command;
import view.Sound;
import view.SubMenu;
import view.SubMenuSelection;
import view.TimedViews;
import view.node.RotatingNode;

/**
 * This class is the sub menu for the entering in the game.
 */
public class SubMenuEnter extends SubMenu {
    private static final String BACKGROUND = "/menuImgs/backgroundEnter.png";
    private static final String NAMEOFGAME = "/menuImgs/titleMenu.png";
    private static final String ISAAC = "/menuImgs/isaac-";
    private static final String FORMAT = ".png";
    private static final long FRAMETIME_ISAAC = 250;
    private static final long FRAMETIME_NAMEOFGAME = 1500;
    private static final double ANGLE_NAMEOFGAME = 1;
    private final Sound s = new SoundJavafx("/menuSound/pageTurn.wav");

    private final TimedViews timeIsaac = new TimedViewsJavafx();
    private final RotatingNode nameOfGameAnimated = new RotatingNodeJavafx();

    /**
     * Create the entering menu.
     * @param selector the selector.
     * @param pnMain the {@link Pane} that contains the other @param.
     * @param nameOfGame the {@link ImageView} for the name of the game.
     * @param isaac the {@link ImageView} for crying Isaac.
     * @param background the image in the background.
     */
    public SubMenuEnter(final SubMenuSelection selector, final Pane pnMain, final ImageView nameOfGame, final ImageView isaac, final ImageView background) {
        super(selector, pnMain);
        background.setImage(new Image(BACKGROUND));
        final AnimatedView isaacAnimated = new AnimatedViewJavafx(isaac);
        nameOfGame.setImage(new Image(NAMEOFGAME));
        setFrames(isaacAnimated, ISAAC, FORMAT);
        timeIsaac.add(isaacAnimated);
        timeIsaac.setMilliseconds(FRAMETIME_ISAAC);
        nameOfGameAnimated.setNode(nameOfGame);
        nameOfGameAnimated.setMilliseconds(FRAMETIME_NAMEOFGAME);
        nameOfGameAnimated.setMaxAngle(ANGLE_NAMEOFGAME);
        timeIsaac.start();
        nameOfGameAnimated.start();
    }

    @Override
    public final void select() {
        timeIsaac.start();
        nameOfGameAnimated.start();
    }

    @Override
    public final void input(final Command c) {
        super.input(c);
        if (c == Command.ENTER && getSelector().contains(SubMenuGame.class)) {
            s.play();
            getSelector().selectSubMenu(SubMenuGame.class);
        }
    }

    @Override
    public final void reset() {
        timeIsaac.stop();
        nameOfGameAnimated.stop();
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
