package model.events;

import model.entity.Entity;
import java.util.Objects;

/**
 * Base class for all the events.
 */
public abstract class AbstractEvent implements Event {
    private final Entity sourceEntity;

    /**
     * Initialize the sources.
     * 
     * @param sourceEntity the source entity
     */
    public AbstractEvent(final Entity sourceEntity) {
        this.sourceEntity = Objects.requireNonNull(sourceEntity);
    }

    @Override
    public final Entity getSourceEntity() {
        return this.sourceEntity;
    }
}
