package view.javafx.game;

import javafx.scene.canvas.GraphicsContext;

/**
 * This view is needed just beacause a Wall Entity exsists, 
 * but it is no required to draw anything because the walls are already in the background of the room.
 */
public class WallView extends AbstractEntityView {

    /**
     * 
     * @param gameView is to which the entity belongs.
     */
    public WallView(final GameView gameView) {
        super(gameView);
    }

    @Override
    public void draw(final GraphicsContext gc) {
    }

}
