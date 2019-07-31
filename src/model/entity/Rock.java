package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.NeutralMentalityComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.EntityEnum;

/**
 * Implements the Rock.
 */
public class Rock extends AbstractStaticEntity {
    private static final double WIDTH = 1;
    private static final double HEIGHT = 1;
    private static final int WEIGHT = 1;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.ROCK;

    /**
     * Empty constructor.
     * the position is 0,0,0.
     */
    public Rock() {
        this(0, 0);
    }

    /**
     * Create a rock based on his position.
     * @param x the x-axis.
     * @param y the y-axis.
     */
    public Rock(final double x, final double y) {
        super();
        this.attachComponent(new NeutralMentalityComponent(this));
        this.setDefaultComponents(new BodyComponent(this, x, y, 0, HEIGHT, WIDTH, WEIGHT),
                new CollisionComponent(this), new StatusComponent(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }
}
