package view.javafx.game.menu;

import javafx.scene.layout.Pane;
import view.SubMenuView;

/**
 * TODO .
 */
public class SubMenuOptionView implements SubMenuView {
    private final Pane main;

    /**
     * TODO .
     */
    public SubMenuOptionView() {
        main = ViewGetter.getNodeByName("pnInGameMenu", Pane.class);
    }
    @Override
    public final Object getMain() {
        return main;
    }

}
