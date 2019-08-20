package controller.menu;

/**
 * Child of the {@link MenuSelectionImpl}.
 */
public interface Child {

    /**
     * Get the father of this child.
     * @return the father.
     */
    MenuSelection<?> getFather();
    /**
     * Used when the child is selected.
     */
    void selectChild();
    /**
     * Used when the child is not selected.
     */
    void disownedChild();
}
