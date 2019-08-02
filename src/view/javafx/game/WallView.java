package view.javafx.game;

import java.util.UUID;

import javafx.scene.canvas.GraphicsContext;

/**
 * This view is needed just beacause a Wall Entity exsists, 
 * but it is no required to draw anything because the walls are already in the background of the room.
 */
public class WallView extends AbstractEntityView {

    /**
     * 
     * @param id 
     * @param gv 
     */
    public WallView(final UUID id, final GameViewImpl gv) {
        super(id, gv);
    }

    /**
     * 
     * @param id 
     */
    public WallView(final UUID id) {
        super(id);
    }

    @Override
    public void draw(final GraphicsContext gc) {
    }

}
