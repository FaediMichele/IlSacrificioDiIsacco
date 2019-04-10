package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.FireComponent;
import model.component.FireType;
import model.component.Mentality;
import model.component.MentalityComponent;

/**
 * Implements the fires.
 */
public class Fire extends AbstractStaticEntity {

    /**
     * Empty constructor.
     * 
     * @param fireType the {@link FireType}
     */
    public Fire(final FireType fireType) {
        super();
        attachComponent(new FireComponent(this, fireType));
        attachComponent(new MentalityComponent(this, Mentality.EVIL));
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param fireType        the {@link FireType}
     */
    public Fire(final BodyComponent entityBody, final CollisionComponent entityCollision, final FireType fireType) {
        this(fireType);
        setDefaultComponents(entityBody, entityCollision);
    }
}
