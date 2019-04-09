package model.entity;

import model.component.BodyComponent;
import model.component.Component;

/**
 * The entity for the tears, they are the main damage dealing entity.
 */
public class Tear extends AbstractMovableEntity {

    /**
     * Basic constructor.
     */
    public Tear() {
        this(new BodyComponent(), null);
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Tear(final BodyComponent entityBody, final Component entityCollision) {
        super(entityBody, entityCollision);
    }
}
