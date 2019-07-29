package model.events;

import model.game.Room;

/**
 * Event launched when the {@link Room} change.
 */
public class RoomChangedEvent {
    private final Room previous;
    private final Room actual;

    /**
     * Event with the previous {@link Room} and the next.
     * @param previous the previous {@link Room} where the player was.
     * @param actual the actual {@link Room} where the player is.
     */
    public RoomChangedEvent(final Room previous, final Room actual) {
        this.previous = previous;
        this.actual = actual;
    }

    /**
     * Get the last {@link Room} used.
     * @return the previous {@link Room}, where the player was.
     */
    public Room getPrevious() {
        return previous;
    }
    /**
     * Get the actual {@link Room} used.
     * @return the actual {@link Room}, where the player is.
     */
    public Room getActual() {
        return actual;
    }
}
