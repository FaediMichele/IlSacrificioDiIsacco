package controller.menu;

import util.Command;
import view.SubMenuView;
import view.javafx.game.menu.SubMenuEnterView;

/**
 * This class is the sub menu for the entering in the game.
 */
public class SubMenuEnter extends SubMenu {
    private static final long FRAMETIME_ISAAC = 250;
    private static final long FRAMETIME_NAMEOFGAME = 1500;
    private static final double ANGLE_NAMEOFGAME = 1;
    private final SubMenuEnterView smv;

    /**
     * Create the entering menu.
     * @param selector the selector.
     */
    public SubMenuEnter(final SubMenuSelection selector) {
        super(selector);
        smv = new SubMenuEnterView(FRAMETIME_ISAAC, FRAMETIME_NAMEOFGAME, ANGLE_NAMEOFGAME);
    }

    @Override
    public final void select() {
        smv.start();
    }

    @Override
    public final void input(final Command c) {
        super.input(c);
        if (c == Command.ENTER && getSelector().contains(SubMenuGameMenu.class)) {
            getSelector().selectSubMenu(SubMenuGameMenu.class);
        }
    }

    @Override
    public final void reset() {
        smv.stop();
    }

    @Override
    public final SubMenuView getSubMenuView() {
        return smv;
    }
}
