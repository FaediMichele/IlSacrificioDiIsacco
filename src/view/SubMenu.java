package view;

import util.Lambda;
import util.Pair;

/**
 * The SubMenu have different behavior based on their implementation.
 * Is used in the UI.
 * The behavior of the method may change a lot base on the implementation.
 */
public interface SubMenu {
    /**
     * Go up.

     */
    void up();

    /**
     * Go down.
     */
    void down();

    /**
     * Go left.
     */
    void left();

    /**
     * Go right.
     */
    void right();

    /**
     * Esc.
     */
    void esc();
    /**
     * Enter.
     */
    void enter();

    /**
     * Go to the initial state.
     */
    void reset();

    /**
     * Every UI object have their behavior. When is selected ( left, right, up, down) and pressed enter the {@link Lambda} run.
     * @param elmentsWithFunction function to call when the object is selected and used.
     */
    void addElements(Pair<Object, Lambda> elmentsWithFunction);
}
