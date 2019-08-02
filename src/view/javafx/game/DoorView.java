package view.javafx.game;

import java.util.UUID;

import javafx.scene.canvas.GraphicsContext;

/**
 * 
 *
 */
public class DoorView extends AbstractEntityView {

    /**
     * 
     * @param id 
     * @param gv 
     */
    public DoorView(final UUID id, final GameViewImpl gv) {
        super(id, gv);
    }

    /**
     * 
     * @param id 
     */
    public DoorView(final UUID id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void draw(final GraphicsContext gc) {
        // TODO Auto-generated method stub

    }

}
