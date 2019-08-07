package view.interfaces;

import java.util.List;

import view.SubMenuView;

/**
 * The interface of the selector view. It manage the animation for change the sub menu.
 */
public interface MainMenuSelectionView {
    /**
     * Play the audio of the sub menu that change.
     */
    void playChanged();

    /**
     * Animate the transition of the sub menu.
     * 
     * @param start the SubMenuView of start
     * @param end   the SubMenuView of end
     */
    void changeSubMenu(SubMenuView start, SubMenuView end);

    /**
     * Jump to the SubMenu.
     * 
     * @param dest the SubMenuView of destination.
     */
    void jumpTo(SubMenuView dest);

    /**
     * Do the operation for the change of the sub menu selection.
     * 
     * @param previous if the selected sub menu selection is this or not.
     */
    void selectSelection(boolean previous);

    /**
     * Set the bind of the sub menu contained.
     * 
     * @param paneOfSubMenu the sub menu panes.
     */
    void setBind(List<Object> paneOfSubMenu);
}
