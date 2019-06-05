package view;

/**
 * The SubMenu have different behavior based on their implementation.
 * Is used in the UI.
 * The behavior of the method may change a lot base on the implementation.
 */
public abstract class SubMenu {
    private final SubMenuSelection sms;
    private final Object main;

    /**
     * Set the selector that controls every sub menu.
     * @param selector the {@link SubMenuSelection}.
     * @param main the main object that represent the menu.
     */
    public SubMenu(final SubMenuSelection selector, final Object main) {
        sms = selector;
        this.main = main;
    }

    /**
     * Get the main object for the sub menu.
     * @return the main object.
     */
    public Object getMain() {
        return main;
    }

    /**
     * Get the {@link SubMenuSelection}.
     * @return the {@link SubMenuSelection}.
     */
    public SubMenuSelection getSelector() {
        return sms;
    }

    /**
     * Go up.

     */
    public void up() {
    }

    /**
     * Go down.
     */
    public void down() {
    }

    /**
     * Go left.
     */
    public void left() {
    }

    /**
     * Go right.
     */
    public void right() {
    }

    /**
     * Esc.
     */
    public void esc() {
    }
    /**
     * Enter.
     */
    public void enter() {
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
}
