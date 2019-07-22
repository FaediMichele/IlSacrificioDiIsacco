package view.javafx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.scene.canvas.Canvas;

/**
 * Class for the main view of the game, it contains a list of all entities to
 * draw.
 *
 */
public class GameViewImpl implements GameView {
    private final List<EntityView> entities = new ArrayList<>();
    private final Canvas cv;

    /**
     * @param cv the Canvas in which the GameView has to draw
     */
    public GameViewImpl(final Canvas cv) {
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
     * Removes an {@link EntityView} from the drawing list.
     * @param entity {@link EntityView}
     */
    public void removeEntity(final EntityView entity) {
        this.entities.remove(entity);
    }

    /**
     * This method must be called by the controller for each of the actual entityViews displayed in the canvas.
     *@param status what isaac is doing
     * @param x position on the x axis
     * @param y position on the y axis
     * @param height of a sprite
     * @param width of a sprite
     * @param entity the entityView to which this parameters must be applied
     */
    public void setEntityViewParameters(final EntityView entity, final String status, final double x, final double y, final double height, final double width) {
        Objects.requireNonNull(entity);
        if (this.entities.contains(entity)) {
            entity.setX(x);
            entity.setY(y);
            entity.setStatus(status);
            entity.setHeight(height);
            entity.setWidth(width);
        } else {
            throw new IllegalArgumentException();
        }
    };

    /**
     * It draws all entities in the canvas.
     */
    public void draw() {
        entities.stream().forEach(e -> e.draw(cv.getGraphicsContext2D()));
    }
}
