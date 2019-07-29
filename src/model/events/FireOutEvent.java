package model.events;

import model.component.FireType;
import model.entity.Entity;

/**
 * Event when the fire is out.
 */
public class FireOutEvent extends AbstractEvent {

    private final FireType fireType;

    /**
     * 
     * @param sourceEntity the {@link Entity}
     * @param fireType     the {@link FireType}
     */
    public FireOutEvent(final Entity sourceEntity, final FireType fireType) {
        super(sourceEntity);
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
