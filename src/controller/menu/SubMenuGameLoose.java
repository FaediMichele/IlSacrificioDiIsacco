package controller.menu;

import java.util.Set;

import util.Command;
import view.interfaces.SubMenuGameLooseView;
import view.interfaces.SubMenuView;
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
    public SubMenuGameLoose(final MenuSelection<SubMenu> selector) {
        super(selector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void input(final Set<Command> c) {
        super.input(c);
        if (c.contains(Command.ENTER) && getFather().getFather().get().contains(MainMenuSelection.class)) {
            getFather().getFather().get().select(MainMenuSelection.class);
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void selectChild() {
        super.selectChild();
        smv.startAudio();
    }
    @Override
    public void unselectChild() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubMenuView getSubMenuView() {
        return smv;
    }

}
