package model.entity;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.Component;
import model.events.Event;
import model.events.EventListener;
import model.game.Room;
import util.EqualsForGetters;
import util.StaticMethodsUtils;

import com.google.common.eventbus.EventBus;

/**
 * The base class for all the entities. See also {@link Entity}
 */
public abstract class AbstractEntity implements Entity {
    private final EventBus eventBus = new EventBus();
    private final Map<Class<? extends Component>, Component> componentsMap;
    private Room room;

    /**
     * .Basic constructor.
     */
    public AbstractEntity() {
        this.componentsMap = new LinkedHashMap<>();
        this.setDefaultComponents(new BodyComponent(this), new CollisionComponent(this));
    }

    /**
     * 
     * @param entityBody      a
     * @param entityCollision s
     */
    public AbstractEntity(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        this();
        this.setDefaultComponents(entityBody, entityCollision);
    }

    @Override
    public final void attachComponent(final Component c) {
        if (this.hasComponent(c.getClass())) {
            detachComponent(getComponent(c.getClass()).get());
        }
        this.componentsMap.put(c.getClass(), c);
    }

    @Override
    public final void detachComponent(final Component c) {
        c.unregisterAllListener();
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
    public final boolean hasComponent(final Class<?> c) {
        return this.componentsMap.containsKey(c);
    }

    @Override
    public final Optional<? extends Component> getComponent(final Class<? extends Component> c) {
        if (this.hasComponent(c)) {
            return Optional.of(c.cast(this.componentsMap.get(c)));
        } else {
            return this.getComponents().stream().filter(cmp -> c.isInstance(cmp)).findFirst();
        }
    }

    @Override
    @EqualsForGetters
    public final List<Component> getComponents() {
        return new LinkedList<Component>(this.componentsMap.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * Sets the default components.
     * 
     * @param entityBody      the body
     * @param entityCollision the collision
     */
    protected final void setDefaultComponents(final BodyComponent entityBody,
            final CollisionComponent entityCollision) {
        this.attachComponent(entityBody);
        this.attachComponent(entityCollision);
    }

    @Override
    public final Room getRoom() {
        return this.room;
    }

    @Override
    public final void changeRoom(final Room r) {
        this.room = r;
    }

    /**
     * HashCode for Entity.
     */
    @Override
    public final int hashCode() {
        return StaticMethodsUtils.hashCode(this);
    }

    /**
     * Equals for Entity.
     */
    @Override
    public final boolean equals(final Object obj) {
        return StaticMethodsUtils.equals(this, obj);
    }
}
