package model.component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import model.entity.Entity;
import model.entity.events.Event;
import model.entity.events.EventListener;
import util.StaticMethodsUtils;

/**
 * Generic fields and methods needed by each component.
 * @param <C> is the type of component you are creating
 */
public abstract class AbstractComponent<C extends Component> implements Component {
    private boolean active;
    private Entity entity;
    private Optional<C> componentReplaced;
    private final List<EventListener<? extends Event>> eventListeners;

    AbstractComponent(final Entity entity) {
        this.entity = entity;
        this.componentReplaced = Optional.empty();
        this.eventListeners = new LinkedList<EventListener<? extends Event>>();
        active = true;
    }

    /**
     * 
     * @param component is the {@link Component} which will be replaced by this new
     *                  component
     */
    AbstractComponent(final Entity entity, final C component) {
        this(entity);
        if (component.getEntity() == entity) {
            this.componentReplaced = Optional.of(component);
        } else {
            throw new IllegalArgumentException(
                    "You cannot replace a component with another component that does not belong to the same entity"); 
            }
        component.unregisterAllListener();
        component.disableComponent();
        entity.detachComponent(component);
        active = true;
    }

    /**
     * 
     * @param eventListener the {@link EventListener}
     */
    protected void registerListener(final EventListener<? extends Event> eventListener) {
        getEntity().registerListener(eventListener);
        this.eventListeners.add(eventListener);
    }

    /**
     * Register all event listener of this component.
     */
    public void registerAllListener() {
        this.eventListeners.forEach(eLis -> getEntity().registerListener(eLis));
    }

    /**
     * disability this component.
     */
    public void disableComponent() {
        this.active = false;
    }

    /**
     * Rehabilitate this component.
     */
    public void rehabilitateComponent() {
        this.active = true;
    }

    /**
     * Unregister all event listener of this component.
     */
    public void unregisterAllListener() {
        this.eventListeners.forEach(eLis -> getEntity().unregisterListener(eLis));
    }

    /**
     * Check if the component is active, this has to be done before each update
     * call.
     * 
     * @return {@link Boolean}.
     */
    public final boolean isActive() {
        return active;
    }

    /**
     * Enable or disable a component by setting a variable.
     * 
     * @param state {@link Boolean}.
     */
    public final void setState(final boolean state) {
        active = state;
    }

    /**
     * Get the entity this component is attached to.
     * 
     * @return {@link Entity}.
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Sets the entity this component has to be attached to.
     * 
     * @param e {@link Entity}
     */
    protected final void setEntity(final Entity e) {
        if (entity != null) {
            throw new IllegalStateException();
        }
        entity = e;
    }

    /**
     * Release all resources used by this component.
     */
    protected final void dispose() {
        // active = false;
        // (new LinkedList<>(this.eventListeners)).forEach(el ->
        // unregisterListener(el));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Double deltaTime) {

    }

    /**
     * Returns the hash code of the {@link Component}.
     */
    @Override
    public final int hashCode() {
        return StaticMethodsUtils.hashCode(this);
    }

    /**
     * Checks if two {@link Component} are equals.
     */
    @Override
    public final boolean equals(final Object obj) {
        return StaticMethodsUtils.equals(this, obj);
    }
}
