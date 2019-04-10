package model.entity.events;

import model.component.Component;
import model.component.FireType;
import model.entity.Entity;

/**
 * Event when the fire is out.
 */
public class FireOutEvent extends AbstractEvent {

    private final FireType fireType;

    /**
     * 
     * @param sourceEntity    the {@link Entity}
     * @param sourceComponent the type of {@link Component}
     * @param fireType        the {@link FireType}
     */
    public FireOutEvent(final Entity sourceEntity, final Class<? extends Component> sourceComponent,
            final FireType fireType) {
        super(sourceEntity, sourceComponent);
        this.fireType = fireType;
    }

    /**
     * 
     * @return the fireType
     */
    public FireType getFireType() {
        return this.fireType;
    }

}
