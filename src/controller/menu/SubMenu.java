package controller.menu;

import java.util.Set;

import util.Command;
import view.SubMenuView;

/**
 * The SubMenu have different behavior based on their implementation.
 * Is used in the UI.
 * The behavior of the method may change a lot base on the implementation.
 */
public abstract class SubMenu implements Child {
    private final MenuSelection<SubMenu> sms;

    /**
     * Set the selector that controls every sub menu.
     * @param selector the {@link SubMenuSelection}.
     */
    public SubMenu(final MenuSelection<SubMenu> selector) {
        sms = selector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuSelection<SubMenu> getFather() {
        return sms;
    }

    /**
     * Pass the {@link Command} clicked.
     * @param c the {@link Command} to manage.
     */
    public void input(final Set<Command> c) {
    }

    /**
     * When the SubMenu is selected.
     */
    public void selectChild() {
    }

    /**
     * Operation to do to dispose all stuff.
     */
    public void close() {
    }

    /**
     * Go to the initial state.
     */
    public abstract void disownedChild();

    /**
     * Get the {@link SubMenuView} that the sub menu use.
     * @return the {@link SubMenuView} used.
     */
    public abstract SubMenuView getSubMenuView();
}
