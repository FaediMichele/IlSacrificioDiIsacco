package controller.menu;

import java.util.Set;

import util.Command;
import view.SubMenuView;
import view.interfaces.SubMenuGameMenuView;
import view.javafx.menu.SubMenuGameMenuViewImpl;

/**
 * This sub menu is used for the "save" menu. (new run, option, continue, ...).
 */
public class SubMenuGameMenu extends SubMenu {
    private static final Object[] ARGS = new Object[] { new Object(), new Object(), new Object()};
    private final SubMenuGameMenuView mgv;

    /**
     * Create the menu to choose the options or new run.
     * @param selector the {@link SubMenuSelection}.
     */
    public SubMenuGameMenu(final MenuSelection<SubMenu> selector) {
        super(selector); 
        mgv = new SubMenuGameMenuViewImpl(ARGS);
    }

    @Override
    public final void input(final Set<Command> c) {
        if (c.contains(Command.ARROW_UP)) {
            up();
        } else if (c.contains(Command.ARROW_DOWN)) {
            down();
        } else if (c.contains(Command.ENTER)) {
            enter();
        } else if (c.contains(Command.EXIT)) {
            exit();
        }
    }

    private void up() {
        mgv.previous();
    }

    private void down() {
        mgv.next();
    }

    private void enter() {
        if (mgv.get().equals(ARGS[0]) && getFather().contains(SubMenuRun.class)) {
            getFather().select(SubMenuRun.class);
        }
        if (mgv.get().equals(ARGS[1]) && getFather().contains(SubMenuOption.class)) {
            getFather().select(SubMenuOption.class);
        }
    }

    private void exit() {
        if (getFather().contains(SubMenuEnter.class)) {
            getFather().select(SubMenuEnter.class);
        }
    }

    @Override
    public final void unselectChild() {
        mgv.initial();
    }

    @Override
    public final SubMenuView getSubMenuView() {
        return mgv;
    }

}
