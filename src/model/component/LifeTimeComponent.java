package model.component;

import model.entity.Entity;
import model.enumeration.BasicStatusEnum;

/**
 * Component for entity that have a time to live.
 */
public class LifeTimeComponent extends AbstractComponent<LifeTimeComponent> {
    private double liveTime;

    /**
     * Create a liveTime time component with entity and time to live.
     * @param entity the entity.
     * @param liveTime the time to live.
     */
    public LifeTimeComponent(final Entity entity, final double liveTime) {
        super(entity);
        this.liveTime = liveTime;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void update(final Double deltaTime) {
        super.update(deltaTime);
        liveTime -= deltaTime;
        if (liveTime <= 0) {
            getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
            getEntity().getRoom().deleteEntity(getEntity());
        }
    }
}
