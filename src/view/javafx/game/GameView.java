package view.javafx.game;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;

/**
 * Class for the main view of the game, it contains a list of all entities to
 * draw.
 *
 */
public class GameView {
    private final List<EntityView> entities = new ArrayList<>();
    private final Canvas cv;

    /**
     * @param cv the Canvas in which the GameView has to draw
     */
    public GameView(final Canvas cv) {
        super();
        this.cv = cv;
    }

    /**
     * Add an {@link EntityView} to draw.
     * @param entity {@link EntityView}
     */
    public void addEntity(final EntityView entity) {
        this.entities.add(entity);
    }

    /**
     * Removes an {@link EntityView} to draw.
     * @param entity {@link EntityView}
     */
    public void removeEntity(final EntityView entity) {
        this.entities.remove(entity);
    }

    /**
     * It draws all entities in the canvas.
     */
    public void draw() {

    }
}
