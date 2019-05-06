package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.Mentality;
import model.component.MentalityComponent;

/**
 * Implements the walls.
 */
public class Wall extends AbstractStaticEntity {

    /**
     * Empty constructor.
     */
    public Wall() {
        super();
        attachComponent(new MentalityComponent(this, Mentality.NEUTRAL));
    }

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Wall(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        setDefaultComponents(entityBody, entityCollision);
    }

}
