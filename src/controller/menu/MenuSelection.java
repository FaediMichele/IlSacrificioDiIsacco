package controller.menu;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Selection of child.
 * Add child and select one by his class.
 * Child with the same class will be overrided.
 *  <p>
 * @param <T> The type of children.
 */
public interface MenuSelection<T> {
    /**
     * Get all children as stream.
     * @return a stream of child.
     */
    Stream<T> asStream();
    /**
     * Get the MenuSelection that contains this.
     * @return father.
     */
    Optional<MenuSelection<?>> getFather();

    /**
     * Get the selected child.
     * @return the selected child.
     */
    T getSelected();

    /**
     * Add children. they should have different class.
     * Otherwise they will be override.
     * @param children the children to add.
     */
    void add(T children);

    /**
     * Know if the menu contains a child.
     * @param possibleChild the child to search.
     * @return true if present.
     */
    boolean contains(Class<?> possibleChild);

    /**
     * Remove child.
     * @param disowneds The child to remove.
     */
    void remove(Class<? extends T> disowneds);

    /**
     * Select a child.
     * @param child the child to select.
     */
    default void select(Class<?> child) {
        select(child, null);
    }

    /**
     * Select a child.
     * @param child the child to select.
     * @param param generic parameter.
     */
    void select(Class<?> child, Object param);
    /**
     * Perform the operation to release all data.
     * When the application ends.
     */
    void close();

    /**
     * Operation made when the child is selected.
     * @param previous the last child.
     * @param next the next child.
     * @param param generic parameter
     */
    void changedChild(T previous, T next, Object param);

    /**
     * Operation made when the grandfather change the selected.
     * @param previous the previous father
     * @param next the next father.
     * @param param generic parameter.
     */
    void fatherChanged(MenuSelection<?> previous, MenuSelection<?> next, Object param);
}
