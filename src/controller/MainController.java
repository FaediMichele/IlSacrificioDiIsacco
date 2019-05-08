package controller;

/**
 * The interface for the MainController.
 */
public interface MainController {
    /**
     * Switch the current active controller with the new one.
     * @param c the new {@link ControllerComponent}
     */
    void switchActive(ControllerComponent c);
}
