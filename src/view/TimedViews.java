package view;

/**
 * Animate {@link AnimatedView}s with a timer.
 */
public interface TimedViews {
    /**
     * Add one or more AnimatedView to the list to be animated with a timer.
     * @param vs {@link AnimatedView}s.
     */
    void add(AnimatedView... vs);

    /**
     * If contains the {@link AnimatedView}.
     * @param vs the {@link AnimatedView} to verify if is cycled. 
     * @return true if the {@link AnimatedView} is memorized. 
     */
    boolean contains(AnimatedView vs);

    /**
     * Set the time of the timer to cycle the {@link AnimatedView}.
     * @param ms milliseconds.
     */
    void setMilliseconds(long ms);

    /**
     * Start the timer.
     */
    void start();

    /**
     * Stop the timer.
     */
    void stop();
}
