package view.interfaces;

import view.SubMenuView;

/**
 * The view for the sub menu when the player win.
 * It make a video start.
 */
public interface SubMenuWinView extends SubMenuView {
    /**
     * Start the video.
     */
    void start();

    /**
     * Stop the video.
     */
    void stop();
}
