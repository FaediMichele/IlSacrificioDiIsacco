package controller.menu;

import java.util.Objects;
import java.util.stream.Collectors;

import view.javafx.game.menu.GameSelectionViewImpl;
import view.menuInterfaces.GameSelectionView;

/**
 *
 */
public class GameSubMenuSelection extends SubMenuSelection {
    private final GameSelectionView gmv;
    private final long msMenu;

    /**
     * 
     * Create the {@link MainMenuSelectionViewImpl}. 
     * @param parent the {@link MenuSelection}.
     * @param msMenu the time for the fade effect.
     */
    public GameSubMenuSelection(final MenuSelection parent, final long msMenu) {
        super(parent);
        gmv = new GameSelectionViewImpl(msMenu);
        this.msMenu = msMenu;

        add(new SubMenuGame(this));
        add(new SubMenuOption(this));

        gmv.setBind(asSet().stream().map(s -> s.getSubMenuView().getMain()).collect(Collectors.toSet()));
    }

    @Override
    public final long getTimeAnimation() {
        return msMenu;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void selectSubMenu(final SubMenu start, final SubMenu end) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void jumpTo(final SubMenu dest) {
        Objects.requireNonNull(dest);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void selectMenu(final SubMenuSelection previous, final SubMenuSelection dest) {
        if (previous.equals(this)) {
            gmv.changeSelector(true);
        }
    }

    /**
     * Start the animation of the fade transition.
     */
    public void startAnimationSelected() {
        gmv.changeSelector(false);
    }

}
