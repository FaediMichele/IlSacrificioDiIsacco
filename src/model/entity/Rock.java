package model.entity;

import model.component.BodyComponent;
import model.component.Component;

/**
 * Implements the Rock.
 */
public class Rock extends AbstractStaticEntity {

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Rock(final BodyComponent entityBody, final Component entityCollision) {
        super(entityBody, entityCollision);
        // TODO Auto-generated constructor stub
    }

}
