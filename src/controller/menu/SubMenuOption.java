package controller.menu;

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

    /**
     * Create a new SubMenuOption.
     * @param selector the Selector .
     */
    public SubMenuOption(final SubMenuSelection selector) {
        super(selector);
        smo = new SubMenuOptionViewImpl(continuePlayingLambda, backToMenuLambda);
    }

    @Override
    public final void input(final Command c) {
        if (c.equals(Command.EXIT)) {
            backToGame();
        }
        if (c.equals(Command.ARROW_DOWN)) {
            smo.down();
        }
        if (c.equals(Command.ARROW_UP)) {
            smo.up();
        }
        if (c.equals(Command.ENTER)) {
            ((Lambda) smo.select()).use();
        }
    }

    private void backToGame() {
        if (getSelector().contains(SubMenuGame.class)) {
            getSelector().selectSubMenu(SubMenuGame.class);
        }
    }
    private void backToMenu() {
        if (getSelector().getParent().contains(MainMenuSelection.class)) {
            System.out.println("tutto ok");
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
