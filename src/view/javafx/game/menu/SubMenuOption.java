package view.javafx.game.menu;

import javafx.scene.layout.Pane;
import view.Command;
import view.SubMenu;
import view.SubMenuSelection;

/**
 * TODO .
 */
public class SubMenuOption extends SubMenu {

    /**
     * TODO .
     * @param selector TODO .
     * @param pnMain the {@link Pane} that contains the other @param.
     */
    public SubMenuOption(final SubMenuSelection selector, final Pane pnMain) {
        super(selector, pnMain);
        // TODO Auto-generated constructor stub
    }

    @Override
    public final void input(final Command c) {
//        switch (c) {
//                    case OPTIONS:
//                        options();
//                        break;
//                    default:
//        }
        if (c.equals(Command.OPTIONS)) {
            options();
        }
    }

    private void options() {
        if (getSelector().contains(SubMenuGame.class)) {
            getSelector().selectSubMenu(SubMenuGame.class);
        }
    }


    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }

}
