package model.events;

import model.game.Floor;

/**
 * Event launched when the {@link Floor} change.
 */
public class FloorChangedEvent {
    private final Floor previous;
    private final Floor actual;

    /**
     * Event with the previous {@link Floor} and the next.
     * @param previous the previous {@link Floor} where the player was.
     * @param actual the actual {@link Floor} where the player is.
     */
    public FloorChangedEvent(final Floor previous, final Floor actual) {
        this.previous = previous;
        this.actual = actual;
    }

    /**
     * Get the last {@link Floor} used.
     * @return the previous {@link Floor}, where the player was.
     */
    public Floor getPrevious() {
        return previous;
    }
    /**
     * Get the actual {@link Floor} used.
     * @return the actual {@link Floor}, where the player is.
     */
    public Floor getActual() {
        return actual;
    }
}
