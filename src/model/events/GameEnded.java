package model.events;

/**
 * Launched when the game ends.
 */
public class GameEnded {
    /**
     * Player win or loose.
     */
    public enum Status { WIN, LOOSE }
    private final Status e;

    /**
     * Set if the player has won or loose.
     * @param e the status
     */
    public GameEnded(final Status e) {
        this.e = e;
    }

    /**
     * Get the status of the player.
     * @return the status.
     */
    public Status getStatus() {
        return e;
    }
}
