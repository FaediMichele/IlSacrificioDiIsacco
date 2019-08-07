package controller.menu;

import java.util.Objects;
import java.util.stream.Collectors;

import view.javafx.game.menu.MainMenuSelectionViewImpl;
import view.menuInterfaces.MainMenuSelectionView;

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
        mmsv.setBind(asSet().stream().map(s -> s.getSubMenuView().getMain()).collect(Collectors.toList()));
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
    public void selectMenu(final SubMenuSelection previous, final SubMenuSelection dest) {
        if (previous.equals(this)) {
            if (dest.getClass().equals(GameSubMenuSelection.class)) {
                mmsv.setReadyToChangeListener(() -> {
                    ((GameSubMenuSelection) dest).startAnimationSelected();
                });
                mmsv.playAudioCharacterSelected();
            }
        } else if (previous.getClass().equals(GameSubMenuSelection.class)) {
            this.selectSubMenu(SubMenuGameMenu.class);
        }
        mmsv.selectSelection(previous.equals(this));

    }

}
