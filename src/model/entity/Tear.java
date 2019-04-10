package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;

/**
 * The entity for the tears, they are the main damage dealing entity.
 */
public class Tear extends AbstractMovableEntity {

    /**
     * Basic constructor.
     */
    public Tear() {
        this(new BodyComponent(this), new CollisionComponent(this));
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Tear(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        super(entityBody, entityCollision);
    }
}
