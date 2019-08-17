package view.interfaces;

import view.SubMenuView;

/**
 * View of the sub menu for the option.
 */
public interface SubMenuOptionView extends SubMenuView {
    /**
     * Go up in the list.
     */
    void up();
    /**
     * Go down on the list.
     */
    void down();
    /**
     * Change to left the selected node. Ff full screen change it.
     */
    void left();

    /**
     * Change to right the selected node. Ff full screen change it.
     */
    void right();
}
