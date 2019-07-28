package model.entity;

import model.component.BodyComponent;
import model.component.FireAIComponent;
import model.component.FireType;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.PsychoMentalityComponent;
import util.enumeration.BasicEntityEnum;
import util.enumeration.EntityEnum;

/**
 * Implements the fires.
 */
public class Fire extends AbstractStaticEntity {
    private static final double WIDTH = 1;
    private static final double HEIGHT = 1;
    private static final int WEIGHT = 1;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.FIRE;

    /**
     * Create a rock based on his position.
     * @param fireType the {@link FireType}.
     * @param x the x-axis.
     * @param y the y-axis.
     */
    public Fire(final FireType fireType, final double x, final double y) {
        super();
        this.attachComponent(new FireAIComponent(this, fireType));
        this.attachComponent(new PsychoMentalityComponent(this));
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
