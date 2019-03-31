package model.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.google.common.eventbus.EventBus;

/**
 * The base class for all the entities.
 * See also {@link Entity}
 */
public abstract class AbstractEntity implements Entity {
    private final EventBus eventBus = new EventBus();
    private final Map<Class<? extends Object>, Object> componentsMap = new LinkedHashMap<>();
    private final Object entityPosition;
    private final Object entityCollision;

    /**
     * Initialize the {@link PositionComponent} and the {@link CollisionComponent}.
     * @param entityPosition {@link PositionComponent}
     * @param entityCollision {@link CollisionComponent}
     */
    public AbstractEntity(final Object entityPosition, final Object entityCollision) {
        this.entityPosition = entityPosition;
        this.entityCollision = entityCollision;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void attach(final Object c) {
        this.componentsMap.put(c.getClass(), c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detach(final Object c) {
        this.componentsMap.remove(c.getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(final Object eventListener) {
        this.eventBus.register(eventListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unregister(final Object eventListener) {
        this.eventBus.unregister(eventListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void post(final Object event) {
        this.eventBus.post(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        //TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean has(final Class<? extends Object> c) {
        return this.componentsMap.containsKey(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Object> get(final Class<? extends Object> c) {
        if (has(c)) {
            return Optional.of(this.componentsMap.get(c));
        } else {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getPosition() {
        return this.entityPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getCollision() {
        return this.entityCollision;
    }

}
