package view.interfaces;

import java.util.List;

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
     * Do the operation for the change of the menuSelection.
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

    /**
     * Set the window at full screen or windowed.
     */
    void changeFullScreen();
}
