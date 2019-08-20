package controller.menu;

import java.util.Set;
import java.util.stream.Collectors;

import util.Command;
import util.Lambda;
import view.interfaces.GameSelectionView;
import view.javafx.menu.GameSelectionViewImpl;

/**
 *
 */
public class GameSelection extends InputMenu<SubMenu> {
    private final GameSelectionView gmv;
    private CharacterInfo characterSelected;
    private final SubMenuGame game;

    /**
     * Create the {@link GameSelection}. 
     * @param msMenu the time for the fade effect.
     */
    public GameSelection(final long msMenu) {
        super();
        gmv = new GameSelectionViewImpl(msMenu);
        game = new SubMenuGame(this);
        add(game);
        add(new SubMenuInGameOption(this, game));
        add(new SubMenuGameLoose(this));
        add(new SubMenuWin(this));
        gmv.selectSubMenu(game);
        gmv.setBind(asStream().map(s -> s.getSubMenuView().getUIMaster()).collect(Collectors.toSet()));
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
        getSelected().input(comms);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changedChild(final SubMenu previous, final SubMenu next, final Object param) {
        super.changedChild(previous, next, param);
        gmv.selectSubMenu(next);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fatherChanged(final MenuSelection<?> previous, final MenuSelection<?> next, final Object param) {
        super.fatherChanged(previous, next, param);
        gmv.changeSelector(previous.equals(this));
        if (!previous.equals(this) && param instanceof CharacterInfo) {
            characterSelected = (CharacterInfo) param;
            this.select(SubMenuGame.class);
            game.loadGame();
        }
        if (previous.equals(this)) {
            game.unselectChild();
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
