package view.javafx.game.menu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import view.AnimatedView;
import view.SubMenuView;
import view.TimedViews;
import view.javafx.AnimatedViewJavafx;
import view.javafx.TimedViewsJavafx;
import view.node.RotatingNode;
import view.node.javafx.RotatingNodeJavafx;

/**
 * View of the sub menu for the initial page of the main menu.
 *
 */
public final class SubMenuEnterView implements SubMenuView {

    private static final String BACKGROUND = "/menuImgs/backgroundEnter.png";
    private static final String NAMEOFGAME = "/menuImgs/titleMenu.png";
    private static final String ISAAC = "/menuImgs/isaac-";
    private static final String FORMAT = ".png";

    private final TimedViews timedIsaac = new TimedViewsJavafx();
    private final RotatingNode nameOfGameAnimated = new RotatingNodeJavafx();

    /**
     * Create a new SubMenu for the initial page of the main menu.
     * @param timeIsaac time in milliseconds of the animation of Isaac.
     * @param timeName time in milliseconds of the animation of the rotating name.
     * @param angle angle of the animation of the rotating name.
     */
    public SubMenuEnterView(final long timeIsaac, final long timeName, final double angle) {
        final ImageView nameOfGame = ViewGetter.getNodeByName("imgNameOfGame", ImageView.class);
        final ImageView isaac = ViewGetter.getNodeByName("imgIsaac", ImageView.class);
        final ImageView background = ViewGetter.getNodeByName("imgBackgroundEnter", ImageView.class);

        background.setImage(new Image(BACKGROUND));
        final AnimatedView isaacAnimated = new AnimatedViewJavafx(isaac);
        nameOfGame.setImage(new Image(NAMEOFGAME));
        setFrames(isaacAnimated, ISAAC, FORMAT);
        timedIsaac.add(isaacAnimated);
        timedIsaac.setMilliseconds(timeIsaac);
        nameOfGameAnimated.setNode(nameOfGame);
        nameOfGameAnimated.setMilliseconds(timeName);
        nameOfGameAnimated.setMaxAngle(angle);
        timedIsaac.start();
        nameOfGameAnimated.start();
    }

    /**
     * Start the animation.
     */
    public void start() {
        timedIsaac.start();
        nameOfGameAnimated.start();
    }

    /**
     * Stop the animation.
     */
    public void stop() {
        timedIsaac.stop();
        nameOfGameAnimated.stop();
    }
    private void setFrames(final AnimatedView av, final String initialName, final String format) {
        int index = 0;
        while (getClass().getResource(initialName + index + format) != null) {
            av.setFrames(new Image(initialName + index + format));
            index++;
        }
    }

    @Override
    public Object getMain() {
        return ViewGetter.getNodeByName("pnEnter", Pane.class);
    }
}
