package view.interfaces;

import view.SubMenuView;
import view.javafx.game.GameView;

/**
 * The view of the sub menu of the game. 
 */
public interface SubMenuGameView extends SubMenuView {
    /**
     * Get the {@link GameView}.
     * @return the {@link GameView}
     */
    GameView getGameView();

    /**
     * Reset the {@link GameView}.
     */
    void reset();

    /**
     * Show a end game picture.
     */
    void gameOver();
}
