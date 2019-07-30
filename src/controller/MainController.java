package controller;

import java.util.List;

import controller.events.Event;
import util.EventListener;

/**
 * The interface for the MainController.
 */
public interface MainController {
    /**
     * Switch the current active controller with the new one.
     * 
     * @param dest the new type of {@link Controller} that will be activated
     */
    void switchActive(Controller dest);

//    /**
//     * Checks if it exists a Controller..
//     * 
//     * @param dest the type of {@link Controller}
//     * @return true if it exists else false
//     */
//    boolean hasController(Class<? extends Controller> dest);


    /**
     * Registers the eventListeners on the EventBus.
     * 
     * @param eventListeners the {@link EventListener}
     */
    void register(List<EventListener<? extends Event>> eventListeners);

    /**
     * Unregisters the eventListeners on the EventBus.
     * 
     * @param eventListeners the {@link EventListener}
     */
    void unregister(List<EventListener<? extends Event>> eventListeners);

    /**
     * Post the event.
     * 
     * @param event {@link Event}
     */
    void postEvent(Event event);

    /**
     * Gets the active {@link Controller}.
     * 
     * @return the {@link Controller}
     */
    Controller getActiveController();

//    /**
//     * Attach a {@link Controller}.
//     * 
//     * @param c the {@link Controller}
//     */
//    void attachController(Controller c);
//
//    /**
//     * Detach a {@link Controller}.
//     * 
//     * @param c the {@link Controller}
//     */
//    void detachController(Class<? extends Controller> c);
}
