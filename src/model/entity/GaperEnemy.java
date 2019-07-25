package model.entity;

import model.component.BodyComponent;
import model.component.FollowAIComponent;
import model.component.MoveComponent;
import model.component.StatusComponent;
import model.component.collision.MovableCollisionComponent;

/**
 * Implements the gaper enemy.
 */
public class GaperEnemy extends AbstractEnemyMovable {
    private static final double WIDTH = 5;
    private static final double HEIGHT = 7;
    private static final int WEIGHT = 5;
    private static final double DSPEED = 7;
    private static final double MAXSPEED = 7;
    private static final double FRICTION = 7;

    /**
     * basic constructor.
     * @param entityBody        body of the enemy
     * @param entityCollision   collision management of the enemy
     * @param entityStatus      status of the enemy
     * {@inheritDoc}
     */
    public <C extends MovableCollisionComponent> GaperEnemy(final BodyComponent entityBody, final C entityCollision, final StatusComponent entityStatus) {
        super(entityBody, entityCollision, entityStatus);
        this.attachComponent(new FollowAIComponent(this));
        this.attachComponent(new MoveComponent(this, DSPEED, MAXSPEED, FRICTION));
    }

    /**
     * Create a gaper enemy based on his position.
     * @param x the x-axis.
     * @param y the y.axis.
     */
    public GaperEnemy(final double x, final double y) {
        super();
        this.attachComponent(new BodyComponent(this, x, y, 0, HEIGHT, WIDTH, WEIGHT));
        this.attachComponent(new MoveComponent(this, DSPEED, MAXSPEED, FRICTION));
        this.attachComponent(new MovableCollisionComponent(this));
        this.attachComponent(new StatusComponent(this));
        this.attachComponent(new FollowAIComponent(this));
    }
}
