package view.javafx.game.menu;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import view.SubMenuView;
import view.javafx.game.GameView;
import view.javafx.game.GameViewImpl;

/**
 * The view of the sub menu of the game. 
 */
public class SubMenuGameView implements SubMenuView {
    private final Canvas cnv;
    private final Image gameOverImg;
    private final GameView gameView;

    /**
     * Create a new SubMenuGameView.
     */
    public SubMenuGameView() {
        this.cnv = ViewGetter.getNodeByName("cnvGame", Canvas.class);
        final Pane main = ViewGetter.getNodeByName("pnGameRun", Pane.class);
        gameOverImg = new Image("/menuImgs/Full_Death_Note.png");
        cnv.widthProperty().bind(main.widthProperty());
        cnv.heightProperty().bind(main.heightProperty());
        gameView = new GameViewImpl(cnv);
    }

    /**
     * Get the {@link GameView}.
     * @return the {@link GameView}
     */
    public GameView getGameView() {
        return gameView;
    }

    /**
     * Reset the {@link GameView}.
     */
    public void reset() {
        gameView.clear();
    }

    /**
     * Show a end game picture.
     */
    public void gameOver() {
        cnv.getGraphicsContext2D().drawImage(gameOverImg, 0, 0);
    }

    @Override
    public final Object getMain() {
        return ViewGetter.getNodeByName("pnGameRun", Pane.class);
    }

}
