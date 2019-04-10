package model.entity.events;

import model.component.Component;
import model.entity.Entity;

/**
 * Events when the fire has been damaged by the player.
 */
public class FireHittedEvent extends AbstractEvent {

    /**
     * 
     * @param sourceEntity the source entity
     * @param sourceComponent the source component
     */
    public FireHittedEvent(final Entity sourceEntity, final Class<? extends Component> sourceComponent) {
        super(sourceEntity, sourceComponent);
    }

}
