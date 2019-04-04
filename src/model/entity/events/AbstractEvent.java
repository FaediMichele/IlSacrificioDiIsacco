package model.entity.events;

import model.component.Component;
import model.entity.Entity;
import java.util.Objects;

/**
 * Base class for all the events.
 */
public abstract class AbstractEvent implements Event {
    private final Entity sourceEntity;
    private final Class<? extends Component> sourceComponent;

    /**
     * Initialize the sources.
     * 
     * @param sourceEntity    the source entity
     * @param sourceComponent the type of source component
     */
    public AbstractEvent(final Entity sourceEntity, final Class<? extends Component> sourceComponent) {
        this.sourceEntity = Objects.requireNonNull(sourceEntity);
        this.sourceComponent = Objects.requireNonNull(sourceComponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity getSourceEntity() {
        return this.sourceEntity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends Component> getSourceComponent() {
        return this.sourceComponent;
    }
}
