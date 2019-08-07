package controller.menu;

import java.io.IOException;

import controller.GameController;
import model.enumeration.BasicPlayerEnum;
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
    }

    @Override
    public final void input(final Command c) {
        switch (c) {
        case OPTIONS:
            options();
        case EXIT:
            gameController.stop();
            getSelector().getParent().select(MainMenuSelection.class);
            break;
        default: 
            if (gameController != null) {
                gameController.input(c);
            }
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void select() {
        super.select();
        final GameSubMenuSelection sel = (GameSubMenuSelection) getSelector();
        final CharacterInfo character = sel.getCharacterInfo();
        try {
            this.gameController = new GameController(this, character.getInfo(), "");
            gameController.start();
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
