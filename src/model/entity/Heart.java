package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.HeartCollectibleComponent;

/**
 * Implements a generic heart.
 */
public class Heart extends AbstractStaticEntity {

    /**
     * 
     * @param entityBody the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Heart(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        setDefaultComponents(entityBody, entityCollision);
    }

    /**
     * Main constructor.
     */
    public Heart() {
        super();
        attachComponent(new HeartCollectibleComponent(this));
    }

}
