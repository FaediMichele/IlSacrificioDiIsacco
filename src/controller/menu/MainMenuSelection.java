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
public class MainMenuSelection extends SubMenuSelection {
    private static final long MSPAGE = 250;
    private final MainMenuSelectionView mmsv;
    private final long msMenu;

    /**
     * Create a new Main menu selection.
     * @param parent the {@link MenuSelection}.
     * @param msMenu the time for the change of the menu selection.
     */
    public MainMenuSelection(final MenuSelection parent, final long msMenu) {
        super(parent);
        mmsv = new MainMenuSelectionViewImpl(MSPAGE, msMenu);
        this.msMenu = msMenu;
        add(new SubMenuEnter(this));
        add(new SubMenuGameMenu(this));
        add(new SubMenuRun(this));
        add(new SubMenuOption(this));
        mmsv.setBind(asSet().stream().map(s -> s.getSubMenuView().getMain()).collect(Collectors.toList()));
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTimeAnimation() {
        return msMenu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectSubMenu(final SubMenu start, final SubMenu end) {
        Objects.requireNonNull(end);
        mmsv.changeSubMenu(start.getSubMenuView(), end.getSubMenuView());
        mmsv.playChanged();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void jumpTo(final SubMenu dest) {
        mmsv.jumpTo(dest.getSubMenuView());
    }

    /**
     * Play a fade animation.
     */
    @Override
    public void selectMenu(final SubMenuSelection previous, final SubMenuSelection dest, final Object param) {
        if (!previous.equals(this) && GameSubMenuSelection.class.isInstance(previous)) {
            this.selectSubMenu(SubMenuGameMenu.class);
        }
        mmsv.selectSelection(previous.equals(this));

    }

}
