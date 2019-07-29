package controller.events;

import controller.Controller;

/**
 * Abstract class for {@link Event}.
 */
public class AbstractEvent implements Event {

    private final Controller sourceController;

    /**
     * 
     * @param sourceController {@link Controller}
     */
    public AbstractEvent(final Controller sourceController) {
        this.sourceController = sourceController;
    }

    @Override
    public final Controller getSourceController() {
        return this.sourceController;
    }

}
