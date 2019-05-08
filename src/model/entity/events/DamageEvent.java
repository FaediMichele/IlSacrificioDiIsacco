package model.entity.events;

import java.util.Optional;

import model.entity.Entity;

/**
 * Event when an entity receives damage.
 * 
 */
public class DamageEvent extends AbstractEvent {

    private Optional<Double> damageValue = Optional.empty();

    /**
     * 
     * @param sourceEntity the {@link Entity} that inflicts damage
     */
    public DamageEvent(final Entity sourceEntity) {
        super(sourceEntity);
    }

    /**
     * 
     * @param sourceEntity  the {@link Entity} that inflicts damage
     * @param damageValue   value of the Damage
     */
    public DamageEvent(final Entity sourceEntity, final double damageValue) {
        super(sourceEntity);
        this.damageValue = Optional.of(damageValue);
    }

    /**
     * 
     * @return the damageValue
     */
    public Optional<Double> getDamageValue() {
        return damageValue;
    }
}
