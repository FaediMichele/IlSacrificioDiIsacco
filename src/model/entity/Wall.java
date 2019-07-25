package model.entity;

import model.component.BodyComponent;
import model.component.ObstacleComponent;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.NeutralMentalityComponent;

/**
 * Implements the walls.
 */
public class Wall extends AbstractStaticEntity {

    private static final double WIDTH = 1;
    private static final double HEIGHT = 1;
    private static final int WEIGHT = 1;

    /**
     * Default constructor.
     * @param x the position.
     * @param y the position.
     */
    public Wall(final int x, final int y) {
        super();
        this.attachComponent(new NeutralMentalityComponent(this));
        this.setDefaultComponents(new BodyComponent(this, x, y, 0, HEIGHT, WIDTH, WEIGHT),
                new CollisionComponent(this), new StatusComponent(this));
    }

}
