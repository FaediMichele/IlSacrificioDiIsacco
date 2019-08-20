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
    public SubMenuWin(final MenuSelection<SubMenu> selector) {
        super(selector);
        smv = new SubMenuWinViewImpl(() -> goToMenu());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void selectChild() {
        super.selectChild();
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
        if (getFather().getFather().get().contains(MainMenuSelection.class)) {
            getFather().getFather().get().select(MainMenuSelection.class);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disownedChild() {
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
