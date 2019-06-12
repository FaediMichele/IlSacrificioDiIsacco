package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.FireComponent;
import model.component.FireType;
import model.component.PsychoMentalityComponent;
import model.component.StatusComponent;

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
        this.attachComponent(new FireComponent(this, fireType));
        this.attachComponent(new PsychoMentalityComponent(this));
    }

    /**
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param fireType        the {@link FireType}
     * @param entityStatus    the {@link StatusComponent}
     */
    public Fire(final BodyComponent entityBody, final CollisionComponent entityCollision, final FireType fireType, final StatusComponent entityStatus) {
        this(fireType);
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }
}
