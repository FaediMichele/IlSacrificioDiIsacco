package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.NeutralMentalityComponent;

/**
 * Implements the Rock.
 */
public class Rock extends AbstractStaticEntity {

    /**
     * Empty constructor.
     */
    public Rock() {
        super();
        this.attachComponent(new NeutralMentalityComponent(this));
    }

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Rock(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        this.setDefaultComponents(entityBody, entityCollision);
    }

}
