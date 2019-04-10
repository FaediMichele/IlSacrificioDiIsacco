package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;

/**
 * Implements the walls.
 */
public class Wall extends AbstractStaticEntity {

    /**
     * Basic constructor.
     */
    public Wall() {
        this(new BodyComponent(this), new CollisionComponent(this));
    }

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Wall(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        super(entityBody, entityCollision);
        // TODO Auto-generated constructor stub
    }

}
