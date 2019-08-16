package model.entity;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import model.component.BodyComponent;
import model.component.Component;
import model.component.StatusComponent;
import model.component.collision.CollisionComponent;
import model.enumeration.EntityEnum;
import model.events.Event;
import model.game.Room;
import util.EqualsForGetters;
import util.EventListener;
import util.NotEquals;
import util.NotHashCode;
import util.StaticMethodsUtils;

import com.google.common.eventbus.EventBus;

import javafx.util.Pair;

/**
 * The base class for all the entities. See also {@link Entity}
 */
public abstract class AbstractEntity implements Entity {
    @NotEquals
    @NotHashCode
    private final UUID id = UUID.randomUUID();
    private final EventBus eventBus = new EventBus();
    private final Map<Class<? extends Component>, Component> componentsMap;

    private Room room;

    /**
     * Basic constructor.
     */
    public AbstractEntity() {
        this.componentsMap = new LinkedHashMap<>();
        this.setDefaultComponents(new BodyComponent(this), new CollisionComponent(this), new StatusComponent(this));
    }

    /**
     * 
     * @param <C>             is extends CollisionComponent
     * @param entityBody      is {@link BodyComponent} of entity
     * @param entityCollision is {@link CollisionComponent} of entity
     * @param entityStatus    is {@link StatusComponent} of entity
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
            detachComponent(c.getClass());
        }
        this.componentsMap.put(c.getClass(), c);
        c.registerAllListener();
        return this;
    }

    @Override
    public final void detachComponent(final Component c) {
        c.unregisterAllListener();
        this.componentsMap.remove(c.getClass());
    }

    @Override
    public final void detachComponent(final Class<? extends Component> c) {
        final Optional<? extends Component> comp = this.getComponent(c);
        if (comp.isPresent()) {
            this.detachComponent(comp.get());
        }
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
          final List<Pair<Class<? extends Component>, Component>> listUpdate = 
                  this.componentsMap.keySet().stream()
                                             .map(x -> new Pair<Class<? extends Component>, Component>(x, this.componentsMap.get(x)))
                                             .collect(Collectors.toList());
          listUpdate.stream().forEach(x -> x.getValue().update(deltaTime));
    }

    @Override
    public final boolean hasComponent(final Class<? extends Component> c) {
        final Optional<? extends Component> comp = this.getComponent(c);
        return comp.isPresent();
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <X extends Component> Optional<X> getComponent(final Class<X> c) {
        if (this.componentsMap.containsKey(c)) {
            return Optional.of(c.cast(this.componentsMap.get(c)));
        } else {
            return (Optional<X>) this.getComponents().stream().filter(cmp -> StaticMethodsUtils.isTypeOf(c, cmp.getClass()) || StaticMethodsUtils.isTypeOf(cmp.getClass(), c)).findFirst();
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
    public final UUID getId() {
        return this.id;
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
        return this.getComponent(StatusComponent.class).get();
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
    /**
     * Is the name of entity.
     * @return entity name.
     */
    public abstract EntityEnum getNameEntity();
}
