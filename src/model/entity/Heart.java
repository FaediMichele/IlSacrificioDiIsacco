package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.HeartCollectibleComponent;
import model.component.StatusComponent;

/**
 * Implements a generic heart.
 */
public class Heart extends AbstractStaticEntity {

    /**
     * 
     * @param entityBody the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param entityStatus    the {@link StatusComponent}
     */
    public Heart(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus) {
        this();
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }

    /**
     * Main constructor.
     */
    public Heart() {
        super();
        this.attachComponent(new HeartCollectibleComponent(this));
    }

}
