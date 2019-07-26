package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collectible.KeyCollectableComponent;
import model.component.collision.CollisionComponent;

/**
 * Key entity that can be collected.
 */
public class Key extends AbstractStaticEntity {
    private static final double WIDTH = 0.5;
    private static final double HEIGHT = 0.5;
    private static final int WEIGHT = 1;

    /**
     * Default constructor.
     * @param x the position.
     * @param y the position.
     */
    public Key(final int x, final int y) {
        super();
        this.attachComponent(new KeyCollectableComponent(this));
        this.setDefaultComponents(new BodyComponent(this, x, y, 0, HEIGHT, WIDTH, WEIGHT),
                new CollisionComponent(this), new StatusComponent(this));
    }
}
