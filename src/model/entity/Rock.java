package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;

/**
 * Implements the Rock.
 */
public class Rock extends AbstractStaticEntity {

    /**
     * Basic constructor.
     */
    public Rock() {
        this(new BodyComponent(), new CollisionComponent());
    }

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Rock(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        super(entityBody, entityCollision);
        // TODO Auto-generated constructor stub
    }

}
