package controller;

/**
 * The interface for the MainController.
 */
public interface MainController {
    /**
     * Switch the current active controller with the new one.
     * 
     * @param dest the new type of {@link Controller} that will be activated
     * @return true if the controller has been correctly switched else false
     */
    boolean switchActive(Class<? extends Controller> dest);

    /**
     * Checks if it exists a Controller..
     * 
     * @param dest the type of {@link Controller}
     * @return true if it exists else false
     */
    boolean hasController(Class<? extends Controller> dest);

    /**
     * Registers the eventListeners on the EventBus.
     * 
     * @param eventListeners the {@link EventListener}
     */
    void register(Object... eventListeners);

    /**
     * Unregisters the eventListeners on the EventBus.
     * 
     * @param eventListeners the {@link EventListener}
     */
    void unregister(Object... eventListeners);

    /**
     * Gets the active {@link Controller}.
     * 
     * @return the {@link Controller}
     */
    Controller getActiveController();

    /**
     * Attach a {@link Controller}.
     * 
     * @param c the {@link Controller}
     */
    void attachController(Controller c);

    /**
     * Detach a {@link Controller}.
     * 
     * @param c the {@link Controller}
     */
    void detachController(Class<? extends Controller> c);
}
