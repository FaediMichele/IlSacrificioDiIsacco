package view.interfaces;

import view.SubMenuView;

/**
 * Interface for the SubMenu for the intro.
 */
public interface SubMenuIntroView extends SubMenuView {
    /**
     * Play the intro.
     */
    void play();
    /**
     * Release the resources.
     */
    void clean();

}
