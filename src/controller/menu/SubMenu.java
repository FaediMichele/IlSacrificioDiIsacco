package controller.menu;

import util.Command;
import view.SubMenuView;

/**
 * The SubMenu have different behavior based on their implementation.
 * Is used in the UI.
 * The behavior of the method may change a lot base on the implementation.
 */
public abstract class SubMenu {
    private final SubMenuSelection sms;

    /**
     * Set the selector that controls every sub menu.
     * @param selector the {@link SubMenuSelection}.
     */
    public SubMenu(final SubMenuSelection selector) {
        sms = selector;
    }

    /**
     * Get the {@link SubMenuSelection}.
     * @return the {@link SubMenuSelection}.
     */
    public SubMenuSelection getSelector() {
        return sms;
    }

    /**
     * Pass the {@link Command} clicked.
     * @param c the {@link Command} to manage.
     */
    public void input(final Command c) {
    }

    /**
     * When the SubMenu is selected.
     */
    public void select() {
    }

    /**
     * Go to the initial state.
     */
    public abstract void reset();

    /**
     * Get the {@link SubMenuView} that the sub menu use.
     * @return the {@link SubMenuView} used.
     */
    public abstract SubMenuView getSubMenuView();
}
