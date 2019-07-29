package controller;

/**
 * The basic interface for all the type of Controller.
 */
public interface Controller {
    /**
     * Activates the controller.
     */
    void run();

    /**
     * Stops the controller.
     */
    void stop();
}
