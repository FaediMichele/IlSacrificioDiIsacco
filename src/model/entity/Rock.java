package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.Mentality;
import model.component.MentalityComponent;

/**
 * Implements the Rock.
 */
public class Rock extends AbstractStaticEntity {

    /**
     * Empty constructor.
     */
    public Rock() {
        super();
        attachComponent(new MentalityComponent(this, Mentality.NEUTRAL));
    }

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Rock(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        setDefaultComponents(entityBody, entityCollision);
    }

}
