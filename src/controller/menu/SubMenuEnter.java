package controller.menu;

import java.util.Set;

import util.Command;
import view.interfaces.SubMenuEnterView;
import view.interfaces.SubMenuView;
import view.javafx.menu.SubMenuEnterViewImpl;

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
    public SubMenuEnter(final MenuSelection<SubMenu> selector) {
        super(selector);
        smv = new SubMenuEnterViewImpl(FRAMETIME_ISAAC, FRAMETIME_NAMEOFGAME, ANGLE_NAMEOFGAME);
    }

    @Override
    public final void selectChild() {
        smv.start();
    }

    @Override
    public final void input(final Set<Command> c) {
        super.input(c);
        if (c.contains(Command.ENTER) && getFather().contains(SubMenuGameMenu.class)) {
            getFather().select(SubMenuGameMenu.class);
        }
    }

    @Override
    public final void unselectChild() {
        smv.stop();
    }

    @Override
    public final SubMenuView getSubMenuView() {
        return smv;
    }
}
