package model.entity.events;

import model.entity.Entity;

/**
 * Event when an entity receives damage.
 * 
 */
public class DamageEvent extends AbstractEvent {

    /**
     * 
     * @param sourceEntity the {@link Entity} that inflicts damage
     */
    public DamageEvent(final Entity sourceEntity) {
        super(sourceEntity);
    }

}
