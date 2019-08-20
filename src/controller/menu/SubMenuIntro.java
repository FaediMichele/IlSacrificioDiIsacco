package controller.menu;

import view.SubMenuView;
import view.interfaces.SubMenuIntroView;
import view.javafx.menu.SubMenuIntroViewImpl;

/**
 * Sub menu for the intro.
 */
public class SubMenuIntro extends SubMenu {
    private final SubMenuIntroView smv;

    /**
     * Create a sub menu intro.
     * @param selector the father.
     */
    public SubMenuIntro(final MenuSelection<SubMenu> selector) {
        super(selector);
        smv = new SubMenuIntroViewImpl(() -> getFather().getFather().get().select(MainMenuSelection.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectChild() {
        super.selectChild();
        smv.play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unselectChild() {
        smv.clean();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubMenuView getSubMenuView() {
        return smv;
    }

}
