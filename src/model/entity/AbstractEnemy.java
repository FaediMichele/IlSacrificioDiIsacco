package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.component.mentality.EnemyMentalityComponent;

/**
 * Base class for all enemies.
 */
public abstract class AbstractEnemy extends AbstractMovableEntity {

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param entityStatus    the {@link StatusComponent}
     */
    public AbstractEnemy(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus) {
        this();
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }

    /**
     * Basic constructor.
     */
    public AbstractEnemy() {
        super();
        this.attachComponent(new EnemyMentalityComponent(this));
    }

}
