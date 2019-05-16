package controller;

/**
 * The basic interface for all the type of Controller.
 */
public interface Controller {
    /**
     * Registers all the {@link EventListener}.
     */
    void registerAll();

    /**
     * Unregisters all the {@link EventListener}.
     */
    void unregisterAll();
}
