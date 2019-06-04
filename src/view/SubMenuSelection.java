package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Select the {@link SubMenu}.
 */
public class SubMenuSelection {
    private final List<SubMenu> menus = new ArrayList<SubMenu>();
    private int index;

    /**
     * Get the selected {@link SubMenu}.
     * @return the selected {@link SubMenu}.
     */
    public SubMenu get() {
        return menus.get(index);
    }

    /**
     * Add as many {@link SubMenu} as you want.
     * @param menus the {@link SubMenu} to add.
     */
    public void add(final SubMenu... menus) {
        this.menus.addAll(Arrays.asList(menus));
    }

    /**
     * Return true if the argument is present.
     * @param s the {@link SubMenu} to verify if is used.
     * @return true or false.
     */
    public boolean contains(final SubMenu s) {
        return menus.contains(s);
    }

    /**
     * Change the selected {@link SubMenu}.
     * @param s the {@link SubMenu} to select.
     */
    public void select(final SubMenu s) {
        if (menus.contains(s)) {
            index = menus.indexOf(s);
        } else {
            throw new IllegalArgumentException("SubMenu not found");
        }
    }
}
