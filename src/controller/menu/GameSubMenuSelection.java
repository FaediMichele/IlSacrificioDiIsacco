package controller.menu;

import java.util.Objects;
import java.util.stream.Collectors;

import view.interfaces.GameSelectionView;
import view.javafx.menu.GameSelectionViewImpl;

/**
 *
 */
public class GameSubMenuSelection extends SubMenuSelection {
    private final GameSelectionView gmv;
    private final long msMenu;
    private CharacterInfo characterSelected;
    private final SubMenuGame game;

    /**
     * 
     * Create the {@link MainMenuSelectionViewImpl}. 
     * @param parent the {@link MenuSelection}.
     * @param msMenu the time for the fade effect.
     */
    public GameSubMenuSelection(final MenuSelection parent, final long msMenu) {
        super(parent);
        this.msMenu = msMenu;
        game = new SubMenuGame(this);
        add(game);
        add(new SubMenuOption(this));
        gmv = new GameSelectionViewImpl(msMenu, () -> {
            this.selectSubMenu(SubMenuGame.class);
            game.startGame();
        });
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
        Objects.requireNonNull(end);
        gmv.selectSubMenu(end);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void jumpTo(final SubMenu dest) {
        Objects.requireNonNull(dest);
        gmv.selectSubMenu(dest);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void selectMenu(final SubMenuSelection previous, final SubMenuSelection dest, final Object param) {
        if (previous.equals(this)) {
            gmv.changeSelector(true);
        }
        if (param instanceof CharacterInfo) {
            characterSelected = (CharacterInfo) param;
        }
    }

    /**
     * Start the animation of the fade transition.
     */
    public void startAnimationSelected() {
        gmv.changeSelector(false);
    }

    /**
     * Get the character passed.
     * @return {@link CharacterInfo}.
     */
    public CharacterInfo getCharacterInfo() {
        return characterSelected;
    }
}
