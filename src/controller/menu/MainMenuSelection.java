package controller.menu;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import util.Command;
import view.interfaces.MainMenuSelectionView;
import view.javafx.menu.MainMenuSelectionViewImpl;

/**
 * SubMenuSelection for the main menu of the game.
 */
public class MainMenuSelection extends InputMenu<SubMenu> {
    private static final long MSPAGE = 250;
    private final MainMenuSelectionView mmsv;

    /**
     * Create a new Main menu selection.
     * @param msMenu the time for the change of the menu selection.
     */
    public MainMenuSelection(final long msMenu) {
        super();
        mmsv = new MainMenuSelectionViewImpl(MSPAGE, msMenu);
        add(new SubMenuEnter(this));
        add(new SubMenuGameMenu(this));
        add(new SubMenuRun(this));
        add(new SubMenuOption(this));
        mmsv.setBind(asStream().map(s -> s.getSubMenuView().getUIMaster()).collect(Collectors.toList()));
    } 

    /**
     * {@inheritDoc}
     */
    @Override
    public void input(final Set<Command> comms) {
        super.input(comms);
        if (comms.contains(Command.FULLSCREEN)) {
            mmsv.changeFullScreen();
        }
        getSelected().input(comms);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changedChild(final SubMenu previous, final SubMenu next, final Object param) {
        Objects.requireNonNull(next);
        mmsv.changeSubMenu(previous.getSubMenuView(), next.getSubMenuView());
        mmsv.playChanged();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fatherChanged(final MenuSelection<?> previous, final MenuSelection<?> next, final Object param) {
        super.fatherChanged(previous, next, param);
        if (!previous.equals(this) && GameSelection.class.isInstance(previous)) {
            this.select(SubMenuGameMenu.class);
        }
        mmsv.selectSelection(previous.equals(this));
    }
}
