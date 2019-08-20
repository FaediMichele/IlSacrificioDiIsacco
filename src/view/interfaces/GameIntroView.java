package view.interfaces;

/**
 * Interface for the game intro view.
 */
public interface GameIntroView {
    /**
     * Effect to change the father.
     * @param ok if the game intro is the next {@link MenuSelection} or the previous
     */
    void changedFather(boolean ok);
}
