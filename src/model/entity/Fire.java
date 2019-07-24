package model.entity;

import model.component.BodyComponent;
import model.component.FireAIComponent;
import model.component.FireType;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.NeutralMentalityComponent;
import model.component.mentality.PsychoMentalityComponent;

/**
 * Implements the fires.
 */
public class Fire extends AbstractStaticEntity {
    private static final double WIDTH = 1;
    private static final double HEIGHT = 1;
    private static final int WEIGHT = 1;

    /**
     * Empty constructor.
     * 
     * @param fireType the {@link FireType}
     */
    public Fire(final FireType fireType) {
        super();
        this.attachComponent(new FireAIComponent(this, fireType));
        this.attachComponent(new PsychoMentalityComponent(this));
    }

    /**
     * Create a rock based on his position.
     * @param fireType the {@link FireType}.
     * @param x the x-axis.
     * @param y the y-axis.
     */
    public Fire(final FireType fireType, final double x, final double y) {
        this(fireType);
        this.setDefaultComponents(new BodyComponent(this, x, y, 0, HEIGHT, WIDTH, WEIGHT),
                new CollisionComponent(this), new StatusComponent(this));
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param fireType        the {@link FireType}
     * @param entityStatus    the {@link StatusComponent}
     */
    public Fire(final BodyComponent entityBody, final CollisionComponent entityCollision, final FireType fireType, final StatusComponent entityStatus) {
        this(fireType);
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }
}
