package util;

/**
 * Class that contains one element.
 *
 * @param <T> the type of the elements
 */
public class Single<T> {
    private T obj;

    /**
     * Create a container for one element.
     * @param t the element
     */
    public Single(final T t) {
        obj = t;
    }

    /**
     * Set the element.
     * @param t the element to set.
     */
    public void set(final T t) {
        obj = t;
    }

    /**
     * Get the element.
     * @return the element contained.
     */
    public T get() {
        return obj;
    }
}
