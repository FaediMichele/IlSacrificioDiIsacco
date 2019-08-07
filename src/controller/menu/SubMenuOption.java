package controller.menu;

import util.Command;
import view.SubMenuView;
import view.javafx.menu.SubMenuOptionView;

/**
 * The sub menu that manage the options of the game.
 */
public class SubMenuOption extends SubMenu {
    private final SubMenuOptionView smo;

    /**
     * Create a new SubMenuOption.
     * @param selector the Selector .
     */
    public SubMenuOption(final SubMenuSelection selector) {
        super(selector);
        smo = new SubMenuOptionView();
    }

    @Override
    public final void input(final Command c) {
        if (c.equals(Command.OPTIONS)) {
            options();
        }
    }

    private void options() {
        if (getSelector().contains(SubMenuGame.class)) {
            getSelector().selectSubMenu(SubMenuGame.class);
        }
    }


    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubMenuView getSubMenuView() {
        return smo;
    }

}
