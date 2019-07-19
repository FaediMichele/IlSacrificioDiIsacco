package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.NeutralMentalityComponent;

/**
 * Implements the walls.
 */
public class Wall extends AbstractStaticEntity {

    /**
     * Empty constructor.
     */
    public Wall() {
        super();
        this.attachComponent(new NeutralMentalityComponent(this));
    }

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param entityStatus    the {@link StatusComponent}
     */
    public Wall(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus) {
        this();
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }

}
