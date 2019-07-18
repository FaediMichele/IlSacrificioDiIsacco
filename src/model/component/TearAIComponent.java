package model.component;

import model.entity.Entity;

/**
 *
 */
public class TearAIComponent extends AbstractAIComponent {

    private int angle;
    /**
     * @param entity this entity
     */
    public TearAIComponent(final Entity entity) {
        this(entity, 0);
    }

    /**
     * @param entity this entity
     * @param angle direction angle of the tear
     */
    public TearAIComponent(final Entity entity, final int angle) {
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
     * {@inheritDoc}
     */
    @Override
    protected void moveUpdate() {
        this.getMoveComponent(this.getEntity()).move(Math.cos(Math.toRadians(this.angle)), Math.sin(Math.toRadians(this.angle)), 0);
    }
}
