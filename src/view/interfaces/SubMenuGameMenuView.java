package view.interfaces;

import view.SubMenuView;

/**
 * The view of the sub menu where select new run or options.
 */
public interface SubMenuGameMenuView extends SubMenuView {
    /**
     * Next item.
     */
    void next();

    /**
     * Previous item.
     */
    void previous();

    /**
     * Get the valued passed in the {@link SubMenuGameMenuViewImpl#SubMenuGameMenuView(Object...)}.
     * @return an object
     */
    Object get();

    /**
     * Go to the initial position.
     */
    void initial();
}
