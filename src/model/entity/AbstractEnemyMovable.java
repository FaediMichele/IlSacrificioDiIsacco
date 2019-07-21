package model.entity;

import model.component.BodyComponent;
import model.component.StatusComponent;
import model.component.collision.MovableCollisionComponent;
import model.component.mentality.EnemyMentalityComponent;

/**
 * Base class for all enemies.
 */
public abstract class AbstractEnemyMovable extends AbstractMovableEntity {

    /**
     * 
     * @param entityBody      the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     * @param entityStatus    the {@link StatusComponent}
     * @param <C>             the extend {@link MovableCollisionComponent}
     */
    public <C extends MovableCollisionComponent> AbstractEnemyMovable(final BodyComponent entityBody,
            final C entityCollision, final StatusComponent entityStatus) {
        super();
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }

    /**
     * Basic constructor.
     */
    public AbstractEnemyMovable() {
        super();
        this.attachComponent(new EnemyMentalityComponent(this));

    }
}
