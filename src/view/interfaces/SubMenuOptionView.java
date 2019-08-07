package view.interfaces;

import view.SubMenuView;

/**
 * View of the sub menu for the in game context menu. 
 */
public interface SubMenuOptionView extends SubMenuView {

    /**
     * Go to the next item.
     */
    void down();

    /**
     * Go to the previous item.
     */
    void up();

    /**
     * Get an object representative the selected item.
     * @return the object for the selected item.
     */
    Object select();

    /**
     * Go to the first item.
     */
    void reset();
}
