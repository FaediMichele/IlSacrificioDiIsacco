package controller.menu;

import java.util.Set;

import util.Command;
import view.interfaces.GameIntroView;
import view.javafx.menu.GameIntroViewImpl;

/**
 * The menu for the introduction.
 */
public final class IntroSelection extends InputMenu<SubMenu> {
    private final SubMenuIntro s;
    private final GameIntroView giv;

    /**
     * Create the SubMenuSelection for the introduction.
     * @param msMenu the time for the fade effect.
     */
    public IntroSelection(final long msMenu) {
        super();
        this.s = new SubMenuIntro(this);
        add(s);
        giv = new GameIntroViewImpl(msMenu, () -> s.close());
    }
    @Override
    public void input(final Set<Command> commands) {
        super.input(commands);
        getFather().get().select(MainMenuSelection.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fatherChanged(final MenuSelection<?> previous, final MenuSelection<?> next, final Object param) {
        super.fatherChanged(previous, next, param);
        if (next.equals(this)) {
            s.selectChild();
        } else {
            s.unselectChild();
        }
        giv.changedFather(next.equals(this));
    }
}
