package view.node;

/**
 * This interface is for the rotating node used in the enter menu.
 */
public interface RotatingNode {
    /**
     * Set node to rotate.
     * @param node the nodes to add. Changed from implementation.
     */
    void setNode(Object node);

    /**
     * Set the time for a complete rotation.
     * @param milliseconds the time for the animation.
     */
    void setMilliseconds(long milliseconds);

    /**
     * Set the minimum and maximum angle for the rotation.
     * @param angle the angle of the rotation.
     */
    void setMaxAngle(double angle);

    /**
     * Start the animation.
     */
    void start();

    /**
     * Stop the animation.
     */
    void stop();
}
