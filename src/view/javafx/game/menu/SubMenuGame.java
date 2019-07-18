package view.javafx.game.menu;

import javafx.scene.layout.Pane;
import view.Command;
import view.SubMenu;
import view.SubMenuSelection;

/**
 * 
 *
 */
public class SubMenuGame extends SubMenu {

    /**
     * 
     * Creates SubMenu with the canvas for the actual game.
     * @param selector the {@link SubMenuSelection}.
     * @param main the {@link Pane}.
     */
    public SubMenuGame(final SubMenuSelection selector, final Pane main) {
        super(selector, main);
    }

    @Override
    public final void input(final Command c) {
        switch (c) {
        case OPTIONS:
            options();
            break;
        default:
        }
    }

    private void options() {
        if (getSelector().contains(SubMenuOption.class)) {
            getSelector().selectSubMenu(SubMenuOption.class);
        }
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
    }

}
