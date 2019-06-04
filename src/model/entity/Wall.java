package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.NeutralMentalityComponent;

/**
 * Implements the walls.
 */
public class Wall extends AbstractStaticEntity {

    /**
     * Empty constructor.
     */
    public Wall() {
        super();
        this.attachComponent(new NeutralMentalityComponent(this));
    }

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Wall(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        this.setDefaultComponents(entityBody, entityCollision);
    }

}
