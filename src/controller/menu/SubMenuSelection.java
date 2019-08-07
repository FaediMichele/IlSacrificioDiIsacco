package controller.menu;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


/**
 * Select the {@link SubMenu}.
 */
public abstract class SubMenuSelection {
    private final Map<Class<? extends SubMenu>, SubMenu> menus = new LinkedHashMap<Class<? extends SubMenu>, SubMenu>();
    private SubMenu selected;
    private final MenuSelection parent;

    /**
     * Create a new SubMenuSelection with the parent.
     * @param parent the {@link MenuSelection};
     */
    public SubMenuSelection(final MenuSelection parent) {
        this.parent = parent;
    }

    /**
     * Get the MenuSelection that contains this sub menu.
     * @return the parent.
     */
    public MenuSelection getParent() {
        return this.parent;
    }

    /**
     * Get the selected {@link SubMenu}.
     * @return the selected {@link SubMenu}.
     */
    public SubMenu get() {
        return selected;
    }

    /**
     * Get a set representing the sub menu contained.
     * @return a {@link LinkedHashSet}.
     */
    public Set<SubMenu> asSet() {
        return new LinkedHashSet<SubMenu>(menus.values());
    }

    /**
     * Add as many {@link SubMenu} as you want.
     * @param menus the {@link SubMenu} to add.
     */
    public void add(final SubMenu... menus) {
        for (int i = 0; i < menus.length; i++) {
            this.menus.put(menus[i].getClass(), menus[i]);
        }
        if (selected == null && menus.length > 0) {
            selected = menus[0];
        }
    }

    /**
     * Return true if the argument is present.
     * @param s the {@link SubMenu} to verify if is used.
     * @return true or false.
     */
    public boolean contains(final Class<? extends SubMenu> s) {
        return menus.containsKey(s);
    }

    /**
     * Change the selected {@link SubMenu}.
     * @param s the {@link SubMenu} to select.
     */
    public void selectSubMenu(final Class<? extends SubMenu> s) {
        if (menus.containsKey(s)) {
            if (selected != null) {
                selected.reset();
            }
            final SubMenu sm = menus.get(s);
            sm.select();
            selectSubMenu(selected, sm);
            selected = sm;
        } else {
            throw new IllegalArgumentException("SubMenu not found");
        }
    }

    /**
     * Get the time that the {@link SubMenuSelection} require for the animation for the change of the sub menu.
     * @return the time that require for the animation.
     */
    public abstract long getTimeAnimation();

    /**
     * Method called when the sub menu is changed. 
     * @param start the previous sub menu.
     * @param end the next sub menu.
     */
    public abstract void selectSubMenu(SubMenu start, SubMenu end);

    /**
     * Jump to the destination sub menu immediately.
     * @param dest the destination.
     */
    public abstract void jumpTo(SubMenu dest);

    /**
     * Method called when the menu is changed.
     * It's invoked for both menu selection.
     * @param previous the previous menu selected. May be this subMenu.
     * @param dest the menu selected. May be this subMenu.
     * @param param paramether passed to the Selection
     */
    public abstract void selectMenu(SubMenuSelection previous, SubMenuSelection dest, Object param);
}
