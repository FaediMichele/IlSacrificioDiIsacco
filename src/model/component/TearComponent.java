package model.component;

import model.entity.Entity;

/**
 *
 */
public class TearComponent extends AbstractComponent<TearComponent>{

    private final Directions direction;
    /**
     * @param entity this entity
     */
    public TearComponent(final Entity entity) {
        this(entity, Directions.UP);
    }

    /**
     * @param entity this entity
     * @param direction to move to 
     */
    public TearComponent(final Entity entity, final Directions direction) {
        super(entity);
        this.direction = direction;
    }

    /**
     * 
     * @return direction
     */
    public Directions getDirection() {
        return direction;
    }

    /**
     * update method makes the tear move in the right direction.
     */
    public void move() {
        switch (direction) {
        case UP:
            this.getMoveComponent(this.getEntity()).move(1, 0, 0);
            break;
        case DOWN:
            this.getMoveComponent(this.getEntity()).move(-1, 0, 0);
            break;
        case RIGHT:
            this.getMoveComponent(this.getEntity()).move(0, +1, 0);
            break;
        case LEFT:
            this.getMoveComponent(this.getEntity()).move(0, -1, 0);
            break;
        default:
            break;
        }
    }

    private MoveComponent getMoveComponent(final Entity e) {
        return ((MoveComponent) e.getComponent(MoveComponent.class).get());
    }
}
