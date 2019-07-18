package view.javafx.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the main view of the game, it contains a list of all entities to
 * draw.
 *
 */
public class GameView {
    private List<AnimatedEntityView> entities = new ArrayList<>();

    /**
     * Add an {@link AnimatedEntityView} to draw.
     * @param entity {@link AnimatedEntityView}
     */
    public void addAnimatedEntity(final AnimatedEntityView entity) {
        this.entities.add(entity);
    }

    /**
     * It draws all entities in the canvas.
     */
    public void draw() {

    }
}
