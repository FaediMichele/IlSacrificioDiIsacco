package view.javafx.game.menu;

import java.io.IOException;

import javax.imageio.ImageIO;

import controller.GameController;
import controller.menu.SubMenu;
import controller.menu.SubMenuSelection;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import util.Command;
import view.javafx.game.BombView;
import view.javafx.game.GameView;
import view.javafx.game.GameViewImpl;

/**
 * 
 *
 */
public class SubMenuGame extends SubMenu {

    private GameView gameView;
    private final Canvas cnv;
    private final Image gameOverImg;
    private final GameController gameController;
    private final Pane main;
    /**
     * 
     * Creates SubMenu with the canvas for the actual game.
     * @param selector the {@link SubMenuSelection}.
     * @param main the {@link Pane}.
     * @param cnv the {@link Canvas}.
     * @throws IOException 
     */
    public SubMenuGame(final SubMenuSelection selector, final Pane main, final Canvas cnv) throws IOException {
        super(selector, main);
        this.main = main;
        this.cnv = cnv;
        this.gameView = new GameViewImpl(main);
        gameView.setCanvas(cnv);
        gameOverImg = SwingFXUtils.toFXImage(ImageIO.read(BombView.class.getResource("/menuImgs/Full_Death_Note.png")), null);
        this.gameController = new GameController(this);
    }

    @Override
    public final void input(final Command c) {
        switch (c) {
        case OPTIONS:
            options();
        case EXIT:
            getSelector().getParent().select(MainMenuSelectionView.class);
            break;
        default: 
            gameController.input(c);
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
        this.gameView = new GameViewImpl(this.main);
        gameView.setCanvas(cnv);
    }

    /**
     * @return the gameView
     */
    public GameView getGameView() {
        return gameView;
    }

    /**
     * sets the GameOver view.
     */
    public void gameOver() {
        // TO-DO
        cnv.getGraphicsContext2D().drawImage(gameOverImg, 0, 0);
    }
}
