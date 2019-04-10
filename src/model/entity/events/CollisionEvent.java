package model.entity.events;

import model.component.Component;
import model.entity.Entity;

/**
 * TODO comment.
 *
 */
public class CollisionEvent extends AbstractEvent {

    /**
     * Create a new event for collision.
     * 
     * @param sourceEntity    the entity that cause this event
     * @param sourceComponent the component that cause this event
     */
    public CollisionEvent(final Entity sourceEntity, final Class<? extends Component> sourceComponent) {
        super(sourceEntity, sourceComponent);
    }

}
