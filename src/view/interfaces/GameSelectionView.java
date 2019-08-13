package view.interfaces;

import java.util.Set;

import controller.menu.SubMenu;
import util.Lambda;

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
     * Get a value that indicate if the audio into has ended or it's already running.
     * @return a boolean.
     */
    boolean isPlayingIntro();

    /**
     * Set a operation to use when the audio ends.
     * @param l {@link Lambda}.
     */
    void setOnIntroEnded(Lambda l);

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

    /**
     * Set the window at full screen or windowed.
     */
    void changeFullScreen();
}
