package controller.menu;

import java.util.Set;

import util.Command;
import view.SubMenuView;
import view.interfaces.SubMenuOptionView;
import view.javafx.menu.SubMenuOptionViewImpl;

/**
 * SubMenuOption that manage the full screen and audio.
 */
public class SubMenuOption extends SubMenu {
    private final SubMenuOptionView smv = new SubMenuOptionViewImpl();

    /**
     * Create a Sub menu option.
     * @param selector the selector.
     */
    public SubMenuOption(final MenuSelection<SubMenu> selector) {
        super(selector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void input(final Set<Command> c) {
        super.input(c);
        if (c.contains(Command.ARROW_DOWN)) {
            smv.down();
        }
        if (c.contains(Command.ARROW_UP)) {
            smv.up();
        }
        if (c.contains(Command.ARROW_LEFT)) {
            smv.left();
        }
        if (c.contains(Command.ARROW_RIGHT)) {
            smv.right();
        }
        if (c.contains(Command.EXIT) && getFather().contains(SubMenuGameMenu.class)) {
            getFather().select(SubMenuGameMenu.class);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unselectChild() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubMenuView getSubMenuView() {
        return smv;
    }

}
