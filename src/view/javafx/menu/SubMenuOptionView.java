package view.javafx.menu;

import javafx.scene.layout.Pane;
import view.SubMenuView;
import view.javafx.ViewGetterUtil;

/**
 * TODO .
 */
public class SubMenuOptionView implements SubMenuView {
    private final Pane main;

    /**
     * TODO .
     */
    public SubMenuOptionView() {
        main = ViewGetterUtil.getNodeByName("pnInGameMenu", Pane.class);
    }
    @Override
    public final Object getMain() {
        return main;
    }

}
