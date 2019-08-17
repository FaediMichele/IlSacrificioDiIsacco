package util;

/**
 * Generic interface for lambda function with parameter.
 *
 * @param <T> the type of the parameter.
 */
public interface Lambdas<T> {
    /**
     * Use the function.
     * @param param the parameter
     */
    void use(T param);
}
