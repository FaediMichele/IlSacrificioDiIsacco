package view.interfaces;

import view.SubMenuView;

/**
 * Interface for the game loose screen.
 * It should only load the image
 */
public interface SubMenuGameLooseView extends SubMenuView {
    /**
     * Start the sound of the death.
     */
    void startAudio();
}
