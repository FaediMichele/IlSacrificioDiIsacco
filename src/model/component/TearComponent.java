package model.component;

import model.entity.Entity;

/**
 *
 */
public class TearComponent extends AbstractComponent<TearComponent> {

    private final int angle;
    /**
     * @param entity this entity
     */
    public TearComponent(final Entity entity) {
        this(entity, 0);
    }

    /**
     * @param entity this entity
     * @param angle direction angle of the tear
     */
    public TearComponent(final Entity entity, final int angle) {
        super(entity);
        this.angle = angle;
    }

    /**
     * 
     * @return direction
     */
    public int getAngle() {
        return this.angle;
    }

    /**
     * update method makes the tear move in the right direction.
     */
    public void move() {
        this.getMoveComponent(this.getEntity()).move(Math.cos(this.angle), Math.sin(this.angle), 0);
    }

    private MoveComponent getMoveComponent(final Entity e) {
        return ((MoveComponent) e.getComponent(MoveComponent.class).get());
    }
}
