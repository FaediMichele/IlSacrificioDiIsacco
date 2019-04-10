package util;

/**
 * Interfaces for the lambdas.
 *
 * @param <E> The type of parameter
 */
public interface Lambda<E> {
    /**
     * What to do.
     * 
     * @param event the parameter.
     */
    void action(E event);
}
