package view.node;

/**
 * This interface is used for the circle effect in the menu.
 * Is used in the menu for select a character.
 */
public interface CircleList {
    /**
     * Add a element to the list.
     * @param o the new element to add to the list ( can be viewed only on the next rotate)
     */
    void addElement(Object o);

    /**
     * Add a list of element to the list.
     * @param o the list of element to add <p> See also {@link #addElement}.
     */
    void addAll(Object... o);

    /**
     * Start the animation of the rotation to the left.
     */
    void rotateLeft();

    /**
     * Start the animation of the rotation to the right.
     */
    void rotateRight();

    /**
     * Set a duration for the animation.
     * @param d generic parameter, all implementation should use their own.
     */
    void setDuration(Object d);

    /**
     * Get the selected element (like the one on the bottom).
     * @return the selected element.
     */
    Object getElement();
}
