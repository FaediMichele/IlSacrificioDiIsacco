package model.entity;

import model.component.BodyComponent;
import model.component.GaperAIComponent;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;

/**
 * Implements the gaper enemy.
 */
public class GaperEnemy extends AbstractEnemy {

    /**
     * basic constructor.
     * @param entityBody        body of the enemy
     * @param entityCollision   collision management of the enemy
     * @param entityStatus      status of the enemy
     */
    public GaperEnemy(final BodyComponent entityBody, final CollisionComponent entityCollision, final StatusComponent entityStatus) {
        super(entityBody, entityCollision, entityStatus);
        this.attachComponent(new GaperAIComponent(this));
    }
}
