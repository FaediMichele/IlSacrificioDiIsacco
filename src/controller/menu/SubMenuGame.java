package controller.menu;

import controller.GameController;
import util.Command;
import view.SubMenuView;
import view.javafx.game.GameView;
import view.javafx.menu.SubMenuGameViewImpl;
import view.menuInterfaces.SubMenuGameView;

/**
 * 
 *
 */
public class SubMenuGame extends SubMenu {
    private final SubMenuGameView smgv;
    private final GameController gameController;
    /**
     * 
     * Creates SubMenu with the canvas for the actual game.
     * @param selector the {@link SubMenuSelection}.
     * @throws IOException 
     */
    public SubMenuGame(final SubMenuSelection selector) {
        super(selector);
        smgv = new SubMenuGameViewImpl();
        this.gameController = new GameController(this);
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
            gameController.input(c);
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void select() {
        super.select();
        gameController.start();
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
