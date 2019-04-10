package model.entity.events;

import model.component.Component;
import model.entity.Entity;

/**
 * .
 *
 */
public class DoorChangeEvent extends AbstractEvent {

    /**
     * .asd .
     * 
     * @param sourceEntity asd
     * @param sourceComponent asd
     */
    public DoorChangeEvent(final Entity sourceEntity, final Class<? extends Component> sourceComponent) {
        super(sourceEntity, sourceComponent);
        // TODO Auto-generated constructor stub
    }

}
