package view.interfaces;

import java.util.Set;

import controller.menu.SubMenu;

/**
 * The view of the Game sub menu selection.
 */
public interface GameSelectionView {

    /**
     * Set the bind of the list of sub panes to the main pane.
     * @param paneOfSubMenu list of pane to bind.
     */
    void setBind(Set<Object> paneOfSubMenu);

    /**
     * Select the sub menu.
     * @param end the sub menu selected.
     */
    void selectSubMenu(SubMenu end);

    /**
     * Change the selector.
     * @param previous if the previous selector is this or not.
     */
    void changeSelector(boolean previous);
}
