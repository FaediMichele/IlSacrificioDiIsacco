package controller.menu;

import java.util.Set;

import util.Command;
import view.SubMenuView;
import view.interfaces.SubMenuGameLooseView;
import view.javafx.menu.SubMenuGameLooseViewImpl;

/**
 * Sub menu for the loose menu.
 */
public class SubMenuGameLoose extends SubMenu {

    private final SubMenuGameLooseView smv = new SubMenuGameLooseViewImpl();

    /**
     * Create a sub menu for the loose screen.
     * @param selector the selector.
     */
    public SubMenuGameLoose(final SubMenuSelection selector) {
        super(selector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void input(final Set<Command> c) {
        super.input(c);
        if (c.contains(Command.ENTER) && getSelector().getParent().contains(MainMenuSelection.class)) {
            getSelector().getParent().select(MainMenuSelection.class);
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void select() {
        super.select();
        smv.startAudio();
    }
    @Override
    public void reset() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubMenuView getSubMenuView() {
        return smv;
    }

}
