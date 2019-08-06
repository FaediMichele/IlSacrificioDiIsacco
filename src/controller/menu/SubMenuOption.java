package view.javafx.game.menu;

import controller.menu.SubMenu;
import controller.menu.SubMenuSelection;
import util.Command;
import view.SubMenuView;

/**
 * The sub menu that manage the options of the game.
 */
public class SubMenuOption extends SubMenu {

    /**
     * Create a new SubMenuOption.
     * @param selector the Selector .
     */
    public SubMenuOption(final SubMenuSelection selector) {
        super(selector);
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
        return null;
    }

}
