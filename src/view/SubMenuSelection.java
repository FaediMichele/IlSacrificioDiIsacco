package view;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Select the {@link SubMenu}.
 */
public abstract class SubMenuSelection {
    private final Map<Class<? extends SubMenu>, SubMenu> menus = new LinkedHashMap<Class<? extends SubMenu>, SubMenu>();
    private SubMenu selected;

    /**
     * Get the selected {@link SubMenu}.
     * @return the selected {@link SubMenu}.
     */
    public SubMenu get() {
        return selected;
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
    public void select(final Class<? extends SubMenu> s) {
        if (menus.containsKey(s)) {
            if (selected != null) {
                selected.reset();
            }
            final SubMenu sm = menus.get(s);
            goTo(selected, sm);
            selected = sm;
        } else {
            throw new IllegalArgumentException("SubMenu not found");
        }
    }

    /**
     * Method called when the sub menu is changed. 
     * @param start the previous sub menu.
     * @param end the next sub menu.
     */
    public abstract void goTo(SubMenu start, SubMenu end);
}
