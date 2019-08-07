package controller.menu;

import util.Command;
import view.SubMenuView;
import view.javafx.game.menu.SubMenuSelectMenuViewImpl;
import view.menuInterfaces.SubMenuSelectMenuView;

/**
 * This sub menu is used for the "save" menu. (new run, option, continue, ...).
 */
public class SubMenuGameMenu extends SubMenu {
    private static final Object[] ARGS = new Object[] { new Object(), new Object()};
    private final SubMenuSelectMenuView mgv;

    /**
     * Create the menu to choose the options or new run.
     * @param selector the {@link SubMenuSelection}.
     */
    public SubMenuGameMenu(final SubMenuSelection selector) {
        super(selector); 
        mgv = new SubMenuSelectMenuViewImpl(ARGS);
    }

    @Override
    public final void input(final Command c) {
        switch (c) {
        case ARROW_UP:
            up();
            break;
        case ARROW_DOWN:
            down();
            break;
        case ENTER:
            enter();
            break;
        case EXIT:
            exit();
            break;
         default:
        }
    }

    private void up() {
        mgv.previous();
    }

    private void down() {
        mgv.next();
    }

    private void enter() {
        if (mgv.get().equals(ARGS[0]) && getSelector().contains(SubMenuRun.class)) {
            getSelector().selectSubMenu(SubMenuRun.class);
        }
        if (mgv.get().equals(ARGS[1]) && getSelector().contains(SubMenuOption.class)) {
            getSelector().selectSubMenu(SubMenuOption.class);
        }
    }

    private void exit() {
        if (getSelector().contains(SubMenuEnter.class)) {
            getSelector().selectSubMenu(SubMenuEnter.class);
        }
    }

    @Override
    public final void reset() {
        mgv.initial();
    }

    @Override
    public final SubMenuView getSubMenuView() {
        return mgv;
    }

}
