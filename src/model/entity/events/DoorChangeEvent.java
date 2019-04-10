package model.entity.events;

import model.component.Component;
import model.entity.Entity;

/**
 * .
 *
 */
public class DoorChangeEvent extends AbstractEvent {

    /**
     * The {@link Event} for door changing.
     * 
     * @param sourceEntity the {@link Entity}
     * @param sourceComponent the type of {@link Component}
     */
    public DoorChangeEvent(final Entity sourceEntity, final Class<? extends Component> sourceComponent) {
        super(sourceEntity, sourceComponent);
    }

}
