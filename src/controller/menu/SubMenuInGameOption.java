package controller.menu;

import java.util.Set;

import util.Command;
import util.Lambda;
import view.interfaces.SubMenuInGameOptionView;
import view.interfaces.SubMenuView;
import view.javafx.menu.SubMenuInGameOptionViewImpl;

/**
 * The sub menu that manage the options of the game.
 */
public class SubMenuInGameOption extends SubMenu {
    private final Lambda continuePlayingLambda = () -> backToGame();
    private final Lambda backToMenuLambda = () -> backToMenu();
    private final SubMenuInGameOptionView smo;
    private final SubMenuGame c;

    /**
     * Create a new SubMenuInGameOption.
     * @param selector the Selector .
     * @param c the Game controller
     */
    public SubMenuInGameOption(final MenuSelection<SubMenu> selector, final SubMenuGame c) {
        super(selector);
        smo = new SubMenuInGameOptionViewImpl(continuePlayingLambda, backToMenuLambda);
        this.c = c;
    }

    @Override
    public final void input(final Set<Command> c) {
        if (c.contains(Command.EXIT)) {
            backToGame();
        } else if (c.contains(Command.ARROW_DOWN)) {
            smo.down();
        } else if (c.contains(Command.ARROW_UP)) {
            smo.up();
        } else if (c.contains(Command.ENTER)) {
            ((Lambda) smo.select()).use();
        }
    }

    private void backToGame() {
        c.getGameController().resume();
        if (getFather().contains(SubMenuGame.class)) {
            getFather().select(SubMenuGame.class);
        }
    }
    private void backToMenu() {
        if (getFather().getFather().get().contains(MainMenuSelection.class)) {
            getFather().getFather().get().select(MainMenuSelection.class);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void unselectChild() {
        smo.reset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubMenuView getSubMenuView() {
        return smo;
    }

}
