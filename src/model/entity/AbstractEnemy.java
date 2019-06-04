package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.EnemyMentalityComponent;

/**
 * Base class for all enemies.
 */
public abstract class AbstractEnemy extends AbstractMovableEntity {

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public AbstractEnemy(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        this.setDefaultComponents(entityBody, entityCollision);
    }

    /**
     * Basic constructor.
     */
    public AbstractEnemy() {
        super();
        this.attachComponent(new EnemyMentalityComponent(this));
    }

}
