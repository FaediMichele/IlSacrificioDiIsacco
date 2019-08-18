package controller.menu;

import java.util.Set;

import util.Command;
import view.SubMenuView;
import view.interfaces.SubMenuWinView;
import view.javafx.menu.SubMenuWinViewImpl;

/**
 * Sum menu for the win video.
 */
public class SubMenuWin extends SubMenu {
    private final SubMenuWinView smv;
    private long startTime;

    /**
     * Initialize.
     * @param selector the selector.
     */
    public SubMenuWin(final SubMenuSelection selector) {
        super(selector);
        smv = new SubMenuWinViewImpl(() -> goToMenu());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void select() {
        super.select();
        startTime = System.nanoTime();
        smv.start();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void input(final Set<Command> c) {
        super.input(c);
        //                                                           // time passed greater than 1 second
        if (c.contains(Command.ENTER) || c.contains(Command.EXIT) || (System.nanoTime() - startTime) / 1e9 > 1) { 
            smv.stop();
            goToMenu();
        }
    }

    private void goToMenu() {
        if (getSelector().getParent().contains(MainMenuSelection.class)) {
            getSelector().getParent().select(MainMenuSelection.class);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        smv.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubMenuView getSubMenuView() {
        return smv;
    }

}
