package model.entity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import model.component.BodyComponent;
import model.component.Component;
import model.entity.events.Event;
import model.entity.events.EventListener;

import com.google.common.eventbus.EventBus;

/**
 * The base class for all the entities. See also {@link Entity}
 */
public abstract class AbstractEntity implements Entity {
    private final EventBus eventBus = new EventBus();
    private final Map<Class<? extends Component>, Component> componentsMap = new LinkedHashMap<>();
    private final BodyComponent entityBody;
    private final Component entityCollision;

    /**
     * Base builder to initialize {@link BodyComponent} and
     * {@link CollisionComponent}.
     */
    public AbstractEntity() {
        this(new BodyComponent(), null);
    }

    /**
     * Initialize the {@link BodyComponent} and the {@link CollisionComponent}.
     * 
     * @param entityBody      {@link BodyComponent}
     * @param entityCollision {@link CollisionComponent}
     */
    public AbstractEntity(final BodyComponent entityBody, final Component entityCollision) {
        this.entityBody = entityBody;
        this.entityBody.setEntity(this);
        this.componentsMap.put(this.entityBody.getClass(), this.entityBody);
        this.entityCollision = entityCollision;
        //this.entityCollision.setEntity(this);
        //this.componentsMap.put(this.entityCollision.getClass(), this.entityCollision);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attachComponent(final Component c) {
        this.componentsMap.put(c.getClass(), c);
        c.setEntity(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detachComponent(final Component c) {
        this.componentsMap.remove(c.getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerListener(final EventListener<? extends Event> eventListener) {
        this.eventBus.register(eventListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unregisterListener(final EventListener<? extends Event> eventListener) {
        this.eventBus.unregister(eventListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postEvent(final Event event) {
        this.eventBus.post(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        this.componentsMap.forEach((k, v) -> v.update(deltaTime));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasComponent(final Class<? extends Component> c) {
        return this.componentsMap.containsKey(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<? extends Component> getComponent(final Class<? extends Component> c) {
        if (hasComponent(c)) {
            return Optional.of(c.cast(this.componentsMap.get(c)));
        } else {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Component> getComponents() {
        return new HashSet<Component>(this.componentsMap.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BodyComponent getBody() {
        return this.entityBody;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getCollision() {
        return this.entityCollision;
    }

    /**
     * @return the name of the class
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * Checks if the two entities are the same or not.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        } else {
            final Entity e = Entity.class.cast(obj);
            return e.getComponents().containsAll(e.getComponents())
                    && e.getComponents().size() == this.getComponents().size();
        }
    }

    /**
     * Calculate the hash code.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
