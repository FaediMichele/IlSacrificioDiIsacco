package view.javafx.game;

/**
 * View and animations of Isaac.
 */

public class CainView extends AbstractPlayerView {

    /**
     * Sets the Cain images to be used for the player.
     * @param gameView is to which the entity belongs.
     */
    public CainView(final GameView gameView) {
        super("/gameImgs/character_003_cain.png", gameView);
    }
}
