package controller.menu;

import java.io.IOException;
import java.util.Set;

import controller.GameController;
import model.enumeration.GameEndStatus;
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
    public final void input(final Set<Command> c) {
        if (!((GameSubMenuSelection) getSelector()).isPlayingIntro()) {
            if (c.contains(Command.OPTIONS)) { 
                options();
            } else if (c.contains(Command.EXIT)) {
                if (gameController != null) {
                    gameController.pause();
                }
                options();
            } else {
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
            this.gameController = new GameController(smgv.createGameView(), character.getInfo(), "Game1",
                    (s) -> smgv.runOnApplicationThread(() -> {
                        if (s.equals(GameEndStatus.LOOSE) && getSelector().contains(SubMenuGameLoose.class)) {
                            getSelector().selectSubMenu(SubMenuGameLoose.class);
                        }
                        if (s.equals(GameEndStatus.WIN) && getSelector().contains(SubMenuWin.class)) {
                            getSelector().selectSubMenu(SubMenuWin.class);
                        }
                    }));
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the game controller.
     * @return the game controller.
     */
    public GameController getGameController() {
        return this.gameController;
    }
    private void options() {
        if (getSelector().contains(SubMenuInGameOption.class)) {
            getSelector().selectSubMenu(SubMenuInGameOption.class);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        smgv.reset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
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
