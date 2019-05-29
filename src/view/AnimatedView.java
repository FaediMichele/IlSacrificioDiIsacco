package view;

/**
 * This make a node with an animation that pass frames.
 * 
 * <p>See also {@link TimedViews}.
 */
public interface AnimatedView {
    /**
     * Set a node to apply the frames.
     * @param n the node.
     */
    void setNode(Object n);

    /**
     * Set the frames. Must be compatible with the used framework.
     * @param frames the frames.
     */
    void setFrames(Object... frames);

    /**
     * Change the frame of the node to the next one.
     */
    void next();
}
