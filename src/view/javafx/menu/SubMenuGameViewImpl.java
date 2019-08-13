package view.javafx.menu;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import model.enumeration.HeartEnum;
import util.Pair;
import view.interfaces.SubMenuGameView;
import view.javafx.ViewGetterUtil;
import view.javafx.game.EntityView;
import view.javafx.game.GameView;
import view.javafx.game.GameViewImpl;
import view.javafx.game.HeartStatisticView;
import view.javafx.game.RoomView;
import view.javafx.game.StatisticView;

/**
 * The implementation view of the sub menu of the game. 
 */
public class SubMenuGameViewImpl implements SubMenuGameView {
    private final Canvas cnv;
    private final Image gameOverImg;
    private final GameView gameView;


    /**
     * Create a new SubMenuGameViewImpl.
     */
    public SubMenuGameViewImpl() {
        this.cnv = ViewGetterUtil.getNodeByName("cnvGame", Canvas.class);
        final Pane main = ViewGetterUtil.getNodeByName("pnGameRun", Pane.class);
        gameOverImg = new Image("/menuImgs/Full_Death_Note.png");
        cnv.widthProperty().bind(main.widthProperty());
        cnv.heightProperty().bind(main.heightProperty());
        gameView = new GameViewImpl(cnv);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameView getGameView() {
        return gameView;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gameOver() {
        cnv.getGraphicsContext2D().drawImage(gameOverImg, 0, 0);
    }

    @Override
    public final Object getMain() {
        return ViewGetterUtil.getNodeByName("pnGameRun", Pane.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameView createGameView() {
        return new GameViewImpl(ViewGetterUtil.getNodeByName("cnvGame", Canvas.class));
    }

}
