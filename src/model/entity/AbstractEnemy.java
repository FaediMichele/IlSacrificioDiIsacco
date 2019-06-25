package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.EnemyMentalityComponent;
import model.component.StatusComponent;

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
