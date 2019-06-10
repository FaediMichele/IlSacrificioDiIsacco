package view.node;

/**
 * This interface is used for the menu with the function of the translation between page (initial, saves, game, option, ...).
 * The translation have a time, see {@link TranslationPages#setMilliseconds(long)}.
 */
public interface TranslationPages {
    /**
     * Add the pages that will be moved based on the selectedPage.
     * @param pages the pages to move.
     */
    void addPage(Object... pages);

    /**
     * True if the pages is contained.
     * @param page the page to search.
     * @return true if is present.
     */
    boolean contains(Object page);

    /**
     * Set the time for the translation.
     * @param ms milliseconds.
     */
    void setMilliseconds(long ms);

    /**
     * Move the other page and the selected in order to make the selected one visible in the window.
     * @param page the destination page.
     */
    void goTo(Object page);

    /**
     * Move the page without animation. 
     * @param page the page of destination.
     */
    void jumpTo(Object page);

    /**
     * Get the selected page.
     * @return the selected page.
     */
    Object getSelected();
}
