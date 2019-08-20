package controller.menu;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Menu selection of {@link MenuSelection<T>}.
 * The operation is possible to override the operation for the select of the child and the disowned child.
 * If the child is 
 * @param <T> Super type of children
 */
public class MenuSelectionImpl<T> implements MenuSelection<T> {
    private final Map<Class<? extends T>, T> children = new LinkedHashMap<>();
    private Optional<MenuSelection<?>> father;
    private T first;
    private boolean firstInserted;

    /**
     * Empty constructor.
     */
    public MenuSelectionImpl() {
        first = null;
        firstInserted = true;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Stream<T> asStream() {
        return children.values().stream();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Optional<MenuSelection<? extends Object>> getFather() {
        return father;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public T getSelected() {
        return first;
    }

    /**
     * {@inheritDoc}.
     */
    @SuppressWarnings("unchecked")
    @Override
    public final void add(final T child) {
        if (child == null) {
            return;
        }
        this.children.put((Class<T>) child.getClass(), child);
        if (child instanceof MenuSelectionImpl<?>) {
            ((MenuSelectionImpl<?>) child).setFather(this);
        }
        if (firstInserted && child != null) {
            first = child;
            firstInserted = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final Class<?> possibleChild) {
        return this.children.containsKey(possibleChild);
    }

    /**
     * {@inheritDoc}.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void remove(final Class<? extends T> disowneds) {
        this.children.remove((Class<T>) disowneds.getClass());
        if (first.equals(disowneds)) {
            if (first instanceof Child) {
                ((Child) first).disownedChild();
            }
            first = null;
        }
    }
    /**
     * {@inheritDoc}.
     */
    @Override
    public void select(final Class<?> child, final Object param) {
        if (!children.containsKey(child)) {
            return;
        }
        if (first instanceof Child) {
            ((Child) first).disownedChild();
        }

        final T selected = children.get(child); 
        final T prev = first;
        if (selected instanceof MenuSelection<?>) {
            if (prev instanceof MenuSelection<?>) {
                ((MenuSelection<?>) prev).fatherChanged((MenuSelection<?>) prev, (MenuSelection<?>) selected, param);
            }
            ((MenuSelection<?>) selected).fatherChanged((MenuSelection<?>) prev, (MenuSelection<?>) selected, param);
        } else {
            changedChild(prev, selected, param);
        }
        first = selected;
        if (first instanceof Child) {
            ((Child) first).selectChild();
        }
    }

    /**
     * Close all the children if is MenuSelection.
     */
    @Override
    public void close() {
        children.values().forEach(c -> {
            if (c instanceof MenuSelection<?>) {
                ((MenuSelection<?>) c).close();
            }
            });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changedChild(final T next, final T previous, final Object param) {
    }
    private void setFather(final MenuSelectionImpl<?> father) {
        this.father = Optional.of(father);
    }

    @Override
    public void fatherChanged(final MenuSelection<?> previous, final MenuSelection<?> next, final Object param) {
    }
}
