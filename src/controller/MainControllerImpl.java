package controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import com.google.common.eventbus.EventBus;
import controller.events.Event;
import util.EventListener;

/**
 * The main controller that changes its behavior with the active controller.
 */
public class MainControllerImpl implements MainController {
    private final EventBus eventBus = new EventBus();
    // private final Map<Class<? extends Controller>, Controller>
    // controllerComponents = new LinkedHashMap<Class<? extends Controller>,
    // Controller>();
    // private Class<? extends Controller> activeController;
    private Controller activeController;

    /**
     * 
     */
    public MainControllerImpl() {
        this.switchActive(new MenuController(this));
    }

//    @Override
//    public final boolean switchActive(final Class<? extends Controller> dest) {
//        if (!this.hasController(dest)) {
//            return false;
//        }
////        this.getActiveController().stop();
////        this.activeController = dest;
////        //this.getActiveController().run();
////        return true;
////    }
//
//    @Override
//    public final boolean hasController(final Class<? extends Controller> dest) {
//        return this.controllerComponents.containsKey(dest);
//    }

    @Override
    public final void switchActive(final Controller dest) {
        Objects.requireNonNull(dest);
        if (this.getActiveController() != null) {
            this.getActiveController().stop();
        }
        this.activeController = dest;
        //this.activeController.run();
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
    public final void postEvent(final Event event) {
        this.eventBus.post(event);
    }

    @Override
    public final Controller getActiveController() {
        // return this.controllerComponents.get(this.activeController);
        return this.activeController;
    }

//    @Override
//    public final void attachController(final Controller c) {
//        if (this.hasController(c.getClass())) {
//            this.detachController(c.getClass());
//        }
//        this.controllerComponents.put(c.getClass(), c);
//    }
//
//    @Override
//    public final void detachController(final Class<? extends Controller> c) {
//        if (this.hasController(c)) {
//            this.controllerComponents.get(c).stop();
//            this.controllerComponents.remove(c);
//        }
//
//    }
}
