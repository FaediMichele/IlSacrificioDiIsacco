package model.entity;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.Component;
import model.entity.events.Event;
import model.entity.events.EventListener;

import com.google.common.eventbus.EventBus;

/**
 * The base class for all the entities. See also {@link Entity}
 */
public abstract class AbstractEntity implements Entity {
    private final EventBus eventBus = new EventBus();
    private final Map<Class<? extends Component>, Component> componentsMap;

    /**
     * .Basic constructor.
     */
    public AbstractEntity() {
        this.componentsMap = new LinkedHashMap<>();
        setDefaultComponents(new BodyComponent(this), new CollisionComponent(this));
    }

    /**
     * 
     * @param entityBody      a
     * @param entityCollision s
     */
    public AbstractEntity(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        setDefaultComponents(entityBody, entityCollision);
    }

    @Override
    public final void attachComponent(final Component c) {
        this.componentsMap.put(c.getClass(), c);
    }

    @Override
    public final void detachComponent(final Component c) {
        this.componentsMap.remove(c.getClass());
    }

    @Override
    public final void registerListener(final EventListener<? extends Event> eventListener) {
        this.eventBus.register(eventListener);
    }

    @Override
    public final void unregisterListener(final EventListener<? extends Event> eventListener) {
        this.eventBus.unregister(eventListener);
    }

    @Override
    public final void postEvent(final Event event) {
        this.eventBus.post(event);
    }

    @Override
    public final void update(final Double deltaTime) {
        this.componentsMap.forEach((k, v) -> v.update(deltaTime));
    }

    @Override
    public final boolean hasComponent(final Class<? extends Component> c) {
        return this.componentsMap.containsKey(c);
    }

    @Override
    public final Optional<? extends Component> getComponent(final Class<? extends Component> c) {
        if (hasComponent(c)) {
            return Optional.of(c.cast(this.componentsMap.get(c)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public final List<Component> getComponents() {
        return new LinkedList<Component>(this.componentsMap.values());
    }

    @Override
    public final String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        } else {
            final Entity e = Entity.class.cast(obj);
            return e.getComponents().equals(this.getComponents());
        }
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    /**
     * Sets the default components.
     * 
     * @param entityBody      the body
     * @param entityCollision the collision
     */
    protected final void setDefaultComponents(final BodyComponent entityBody,
            final CollisionComponent entityCollision) {
        attachComponent(entityBody);
        attachComponent(entityCollision);
    }
}
