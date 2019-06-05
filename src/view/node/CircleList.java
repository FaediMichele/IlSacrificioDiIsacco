package view.node;

/**
 * This interface is used for the circle effect in the menu.
 * Is used in the menu for select a character.
 */
public interface CircleList {

    /**
     * Set the width of the circle.
     * @param width the with of the circle({@link Node} will get out of the value for half)
     */
    void setWidth(double width);

    /**
     * Get the width of the circle.
     * @return the width
     */
    double getWidth();

    /**
     * Set the height of the circle.
     * @param height the height of the circle({@link Node} will get out of the value for half)
     */
    void setHeight(double height);

    /**
     * Get the height of the circle.
     * @return the height
     */
    double getHeight();

    /**
     * Set the distance from the left margin.
     * @param posX the position of the circle
     */
    void setMarginLeft(double posX);

    /**
     * Set the distance from the top margin.
     * @param posY the position of the circle.
     */
    void setMarginTop(double posY);

    /**
     * Get the position of the circle.
     * @return the position of the left margin.
     */
    double getMarginLeft();

    /**
     * Get the position of the circle.
     * @return the position of the top margin.
     */
    double getMarginTop();

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

    /**
     * Get a element with index.
     * @param index the index of the node to get. If the circle is rotated also the index change.
     * @return the element with the index.
     */
    Object getElement(int index);

    /**
     * Get the size of the circle.
     * @return the size.
     */
    int size();
}
