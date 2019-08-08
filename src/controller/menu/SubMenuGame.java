package controller.menu;

import java.io.IOException;

import controller.GameController;
import util.Command;
import view.SubMenuView;
import view.interfaces.SubMenuGameView;
import view.javafx.game.GameView;
import view.javafx.menu.SubMenuGameViewImpl;

/**
 * 
 *
 */
public class SubMenuGame extends SubMenu {
    private final SubMenuGameView smgv;
    private GameController gameController;
    /**
     * 
     * Creates SubMenu with the canvas for the actual game.
     * @param selector the {@link SubMenuSelection}.
     * @throws IOException 
     */
    public SubMenuGame(final SubMenuSelection selector) {
        super(selector);
        smgv = new SubMenuGameViewImpl();
        ((GameSubMenuSelection) getSelector()).setOnIntroEnded(() -> gameController.start());
    }

    @Override
    public final void input(final Command c) {
        if (!((GameSubMenuSelection) getSelector()).isPlayingIntro()) {
            switch (c) {
            case OPTIONS:
                options();
            case EXIT:
                if (gameController != null) {
                    gameController.stop();
                }
                options();
                break;
            default: 
                if (gameController != null) {
                    gameController.input(c);
                }
            }
        }
    }

    /**
     * Start the game controller.
     */
    public void loadGame() {
        final GameSubMenuSelection sel = (GameSubMenuSelection) getSelector();
        final CharacterInfo character = sel.getCharacterInfo();
        try {
            this.gameController = new GameController(getGameView(), character.getInfo(), "Game1");
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.out.println("ERRORE FATALE");
        }
    }

    private void options() {
        if (getSelector().contains(SubMenuOption.class)) {
            getSelector().selectSubMenu(SubMenuOption.class);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        smgv.reset();
        ((GameSubMenuSelection) getSelector()).setOnIntroEnded(null);
        if (gameController != null) {
            gameController.stop();
            gameController = null;
        }
    }

    /**
     * @return the gameView
     */
    public GameView getGameView() {
        return smgv.getGameView();
    }

    /**
     * sets the GameOver view.
     */
    public void gameOver() {
        smgv.gameOver();
    }

    @Override
    public final SubMenuView getSubMenuView() {
        return smgv;
    }
}
