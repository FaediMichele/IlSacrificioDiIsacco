package model.entity;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import model.component.BodyComponent;
import model.component.Component;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.events.Event;
import model.game.Room;
import util.EqualsForGetters;
import util.EventListener;
import util.StaticMethodsUtils;

import com.google.common.eventbus.EventBus;

/**
 * The base class for all the entities. See also {@link Entity}
 */
public abstract class AbstractEntity implements Entity {
    private final EventBus eventBus = new EventBus();
    private final Map<Class<? extends Component>, Component> componentsMap;
    private final UUID uuid = UUID.randomUUID();
    private Room room;

    /**
     * .Basic constructor.
     */
    public AbstractEntity() {
        this.componentsMap = new LinkedHashMap<>();
        this.setDefaultComponents(new BodyComponent(this), new CollisionComponent(this), new StatusComponent(this));

    }

    /**
     * 
     * @param <C>             is extends CollisionComponent
     * @param entityBody      a
     * @param entityCollision s
     * @param entityStatus    s
     */
    public <C extends CollisionComponent> AbstractEntity(final BodyComponent entityBody, final C entityCollision,
            final StatusComponent entityStatus) {
        this();
        Objects.requireNonNull(entityBody);
        Objects.requireNonNull(entityCollision);
        Objects.requireNonNull(entityStatus);
        this.setDefaultComponents(entityBody, entityCollision, entityStatus);
    }

    @Override
    public final Entity attachComponent(final Component c) {
        if (this.hasComponent(c.getClass())) {
            detachComponent(getComponent(c.getClass()).get());
        }
        this.componentsMap.put(c.getClass(), c);
        return this;
    }

    @Override
    public final void detachComponent(final Component c) {
        c.unregisterAllListener();
        this.componentsMap.remove(c.getClass());
    }

    @Override
    public final void detachComponent(final Class<? extends Component> c) {
        this.detachComponent(this.componentsMap.get(c));
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
        return this.getComponent(c).isPresent();
    }

    @Override
    public final Optional<? extends Component> getComponent(final Class<? extends Component> c) {
        if (this.componentsMap.containsKey(c)) {
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

    @Override
    public final Room getRoom() {
        return this.room;
    }

    @Override
    public final UUID getUUID() {
        return uuid;
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

    /**
     * {@inheritDoc}
     */
    public StatusComponent getStatusComponent() {
        return (StatusComponent) this.getComponent(StatusComponent.class).get();
    }

    /**
     * Sets the default components.
     * 
     * @param entityBody      the body
     * @param entityCollision the collision
     * @param statusComponent the status
     */
    protected final void setDefaultComponents(final BodyComponent entityBody, final CollisionComponent entityCollision,
            final StatusComponent statusComponent) {
        this.attachComponent(entityBody);
        this.attachComponent(entityCollision);
        this.attachComponent(statusComponent);
    }
}
