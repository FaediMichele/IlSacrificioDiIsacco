package controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.eventbus.EventBus;

import controller.events.Event;
import util.EventListener;

/**
 * The main controller that changes its behavior with the active controller.
 */
public class MainControllerImpl implements MainController {
    private final EventBus eventBus = new EventBus();
    private final Map<Class<? extends Controller>, Controller> controllerComponents = new LinkedHashMap<Class<? extends Controller>, Controller>();
    private Class<? extends Controller> activeController;

    @Override
    public final boolean switchActive(final Class<? extends Controller> dest) {
        if (!this.hasController(dest)) {
            return false;
        }
        this.controllerComponents.get(this.activeController).unregisterAll();
        this.activeController = dest;
        this.controllerComponents.get(this.activeController).registerAll();
        return true;
    }

    @Override
    public final boolean hasController(final Class<? extends Controller> dest) {
        return this.controllerComponents.containsKey(dest);
    }

    @Override
    public final void register(final List<EventListener<? extends Event>> eventListeners) {
        Arrays.asList(eventListeners).forEach(e -> this.eventBus.register(e));
    }

    @Override
    public final void unregister(final List<EventListener<? extends Event>> eventListeners) {
        Arrays.asList(eventListeners).forEach(e -> this.eventBus.unregister(e));
    }

    @Override
    public final Controller getActiveController() {
        return this.controllerComponents.get(this.activeController);
    }

    @Override
    public final void attachController(final Controller c) {
        if (this.hasController(c.getClass())) {
            this.detachController(c.getClass());
        }
        this.controllerComponents.put(c.getClass(), c);
    }

    @Override
    public final void detachController(final Class<? extends Controller> c) {
        if (this.hasController(c)) {
            this.controllerComponents.get(c).unregisterAll();
            this.controllerComponents.remove(c);
        }

    }
}
