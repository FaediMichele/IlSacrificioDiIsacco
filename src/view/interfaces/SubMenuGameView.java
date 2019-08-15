package view.interfaces;

import util.Lambda;
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

    /**
     * Create a new Game View.
     * @return the new Game View.
     */
    GameView createGameView();

   /**
    * Perform an operations that use the application within the thread application.
    * @param l the operations.
    */
    void runOnApplicationThread(Lambda l);
}
