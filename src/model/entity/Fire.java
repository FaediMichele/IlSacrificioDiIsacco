package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.FireComponent;
import model.component.Mentality;
import model.component.MentalityComponent;

/**
 * Implements the fires.
 */
public class Fire extends AbstractStaticEntity {

    /**
     * Empty constructor.
     */
    public Fire() {
        super();
        attachComponent(new FireComponent(this));
        attachComponent(new MentalityComponent(this, Mentality.EVIL));
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Fire(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        setDefaultComponents(entityBody, entityCollision);
    }
}
