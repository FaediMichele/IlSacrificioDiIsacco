package view.javafx.game.menu;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import view.Command;
import view.SubMenu;
import view.SubMenuSelection;
import view.javafx.game.GameView;
import view.javafx.game.GameViewImpl;

/**
 * 
 *
 */
public class SubMenuGame extends SubMenu {

    @SuppressWarnings("unused")
    private GameView gameView;
    private final Canvas cnv;
    /**
     * 
     * Creates SubMenu with the canvas for the actual game.
     * @param selector the {@link SubMenuSelection}.
     * @param main the {@link Pane}.
     * @param cnv the {@link Canvas}.
     */
    public SubMenuGame(final SubMenuSelection selector, final Pane main, final Canvas cnv) {
        super(selector, main);
        this.cnv = cnv;
        this.reset();
    }

    @Override
    public final void input(final Command c) {
        switch (c) {
        case OPTIONS:
            options();
            break;
        case KEY_DOWN:
            break;
        case KEY_UP:
            break;
        case KEY_RIGHT:
            break;
        case KEY_LEFT:
            break;
        case ARROW_DOWN:
            break;
        case ARROW_UP:
            break;
        case ARROW_LEFT:
            break;
        case ARROW_RIGHT:
            break;
        case BOMB:
            break;
        default:
        }
    }

    private void options() {
        if (getSelector().contains(SubMenuOption.class)) {
            getSelector().selectSubMenu(SubMenuOption.class);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.gameView = new GameViewImpl();
        gameView.setCanvas(cnv);
    }

}
