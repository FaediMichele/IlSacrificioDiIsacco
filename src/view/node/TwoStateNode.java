package view.node;

/**
 * Node for the on off option.
 */
public interface TwoStateNode {

    /**
     * Go to on.
     */
    void on();

    /**
     * Go to off.
     */
    void off();

    /**
     * Change the state.
     */
    void change();

    /**
     * Get the state.
     * @return true if on.
     */
    boolean state();
}
