package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.events.Event;
import util.EventListener;

/**
 * Abstract class for the {@link Controller}.
 */
public class AbstractController implements Controller {
    private final MainController main;
    private final List<EventListener<? extends Event>> eventListeners = new ArrayList<EventListener<? extends Event>>();

    /**
     * Basic constructor.
     * 
     * @param main {@link MainController}
     */
    public AbstractController(final MainController main) {
        this.main = main;
    }

    @Override
    public final void registerAll() {
        this.eventListeners.forEach(el -> this.main.register(Arrays.asList(el)));
    }

    @Override
    public final void unregisterAll() {
        this.eventListeners.forEach(el -> this.main.unregister(Arrays.asList(el)));
    }

}
