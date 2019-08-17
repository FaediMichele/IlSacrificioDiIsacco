package model.component.collision;

import model.entity.Entity;
import model.enumeration.BasicStatusEnum;
import model.events.CollisionEvent;

/**
 * 
 * is collision component of tear.
 *
 */
public class TearCollisionComponent extends CollisionComponent {

    /**
     * 
     * @param entity to which this component belongs.
     */
    public TearCollisionComponent(final Entity entity) {
        super(entity);
    }

    /**
     * If the tear collides with an entity from which it can receive damage it disappears.
     */
    @Override
    protected void damage(final CollisionEvent event) {
        getEntity().getStatusComponent().setStatus(BasicStatusEnum.DISAPPEAR);
        getEntity().getRoom().deleteEntity(getEntity());
    }
}
