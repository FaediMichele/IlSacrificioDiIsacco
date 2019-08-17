package controller.menu;

import java.util.Set;

import util.Command;
import util.Lambda;
import view.SubMenuView;
import view.interfaces.SubMenuOptionView;
import view.javafx.menu.SubMenuOptionViewImpl;

/**
 * The sub menu that manage the options of the game.
 */
public class SubMenuOption extends SubMenu {
    private final Lambda continuePlayingLambda = () -> backToGame();
    private final Lambda backToMenuLambda = () -> backToMenu();
    private final SubMenuOptionView smo;
    private final SubMenuGame c;

    /**
     * Create a new SubMenuOption.
     * @param selector the Selector .
     * @param c the Game controller
     */
    public SubMenuOption(final SubMenuSelection selector, final SubMenuGame c) {
        super(selector);
        smo = new SubMenuOptionViewImpl(continuePlayingLambda, backToMenuLambda);
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
        if (getSelector().contains(SubMenuGame.class)) {
            getSelector().selectSubMenu(SubMenuGame.class);
        }
    }
    private void backToMenu() {
        if (getSelector().getParent().contains(MainMenuSelection.class)) {
            getSelector().getParent().select(MainMenuSelection.class);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
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
