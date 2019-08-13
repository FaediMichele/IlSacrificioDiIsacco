package controller.menu;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import util.Command;
import util.Lambda;
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
        gmv = new GameSelectionViewImpl(msMenu);
        game = new SubMenuGame(this);
        add(game);
        add(new SubMenuOption(this));
        gmv.setBind(asSet().stream().map(s -> s.getSubMenuView().getMain()).collect(Collectors.toSet()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void input(final Set<Command> comms) {
        super.input(comms);
        if (comms.contains(Command.FULLSCREEN)) {
            gmv.changeFullScreen();
        }
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
        gmv.changeSelector(previous.equals(this));
        if (!previous.equals(this) && param instanceof CharacterInfo) {
            characterSelected = (CharacterInfo) param;
            this.selectSubMenu(SubMenuGame.class);
            game.loadGame();
        }
        if (previous.equals(this)) {
            game.reset();
        }
    }

    /**
     * Get the character passed.
     * @return {@link CharacterInfo}.
     */
    public CharacterInfo getCharacterInfo() {
        return characterSelected;
    }

    /**
     * Set a listener for the end of the intro.
     * @param l the listener.
     */
    public void setOnIntroEnded(final Lambda l) {
        gmv.setOnIntroEnded(l);
    }

    /**
     * Get a value that indicate if the intro of the game has ended.
     * @return true if the intro is still playing.
     */
    public boolean isPlayingIntro() {
        return gmv.isPlayingIntro();
    }
}
