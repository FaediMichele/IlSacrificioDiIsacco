package view.interfaces;

import java.util.Set;

import controller.menu.CharacterInfo;

/**
 * View of the SubMenu of the run part of the main menu.
 */
public interface SubMenuRunView extends SubMenuView {

    /**
     * Set the character to show.
     * @param set the list of the character.
     */
    void setInfo(Set<CharacterInfo> set);

    /**
     * Swipe to left.
     */
    void left();

    /**
     * Swipe to right.
     */
    void right();

    /**
     * Get the selected info (and random the list if necessary).
     * @return the info of the selected character
     */
    CharacterInfo getSelected();

    /**
     * Get the selected info (don't random the list).
     * @return the info of the selected character
     */
    CharacterInfo getInfoSelected();

    /**
     * Update the info.
     * @param c the info.
     */
    void update(CharacterInfo c);

    /**
     * Reset the list.
     */
    void reset();
}
