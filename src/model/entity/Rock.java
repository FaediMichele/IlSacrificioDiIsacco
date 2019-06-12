package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.NeutralMentalityComponent;
import model.component.StatusComponent;

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
     * @param entityStatus    the {@link StatusComponent}
     */
    public Rock(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus) {
        this();
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }

}
