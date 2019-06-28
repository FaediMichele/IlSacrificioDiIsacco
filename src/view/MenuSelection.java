package view;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Select the {@link SubMenuSelection}.
 */
public class MenuSelection {
    private final Map<Class<? extends SubMenuSelection>, SubMenuSelection> menus =
            new LinkedHashMap<Class<? extends SubMenuSelection>, SubMenuSelection>();
    private SubMenuSelection selected;

    /**
     * Get the selected {@link SubMenuSelection}.
     * @return the selected {@link SubMenuSelection}.
     */
    public SubMenuSelection get() {
        return selected;
    }

    /**
     * Get a set representing the sub menu contained.
     * @return a {@link LinkedHashSet}.
     */
    public Set<SubMenuSelection> asSet() {
        return new LinkedHashSet<SubMenuSelection>(menus.values());
    }

    /**
     * Add as many {@link SubMenuSelection} as you want.
     * @param menus the {@link SubMenuSelection} to add.
     */
    public void add(final SubMenuSelection... menus) {
        for (int i = 0; i < menus.length; i++) {
            this.menus.put(menus[i].getClass(), menus[i]);
        }
        if (selected == null && menus.length > 0) {
            selected = menus[0];
        }
    }

    /**
     * Return true if the argument is present.
     * @param s the {@link SubMenuSelection} to verify if is used.
     * @return true or false.
     */
    public boolean contains(final Class<? extends SubMenuSelection> s) {
        return menus.containsKey(s);
    }

    /**
     * Change the selected {@link SubMenuSelection}.
     * @param s the {@link SubMenuSelection} to select.
     */
    public void select(final Class<? extends SubMenuSelection> s) {
        if (menus.containsKey(s)) {
            final SubMenuSelection sms = menus.get(s);
            goTo(selected.getClass(), s);
            selected = sms;
        } else {
            throw new IllegalArgumentException("SubMenuSelection not found");
        }
    }

    /**
     * Method called when the menu is changed. 
     * @param start the previous sub menu.
     * @param end the next sub menu.
     */
    public void goTo(final Class<? extends SubMenuSelection> start, final Class<? extends SubMenuSelection> end) {
        Objects.requireNonNull(menus.get(start));
        Objects.requireNonNull(menus.get(end));
        menus.get(start).selectMenu(menus.get(start), menus.get(end));
        menus.get(end).selectMenu(menus.get(start), menus.get(end));
    }
}
