package view.javafx.game;

/**
 * View and animations of a Isaac.
 *
 */
public class IsaacView extends AbstractPlayerView {

    /**
     * Sets the Cain images to be used for the player.
     * @param gameView is to which the entity belongs.
     */
    public IsaacView(final GameView gameView) {
        super("/gameImgs/character_001_isaac.png", gameView);
    }
}
