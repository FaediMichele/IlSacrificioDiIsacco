package model.component;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import model.entity.Entity;
import model.events.Event;
import util.EventListener;
import util.StaticMethodsUtils;

/**
 * Generic fields and methods needed by each component.
 * 
 */
public abstract class AbstractComponent implements Component {
    private Entity entity;
    private final List<EventListener<? extends Event>> eventListeners;

    /**
     * Basic builder for components that do not need to know 
     * the entity they are associated with.
     */
    public AbstractComponent() {
        this.eventListeners = new LinkedList<EventListener<? extends Event>>();
    }

    /**
     * This is the father of all the components.
     * @param entity to which this component belongs
     */
    public AbstractComponent(final Entity entity) {
        this();
        Objects.requireNonNull(entity);
        this.entity = entity;

    }

    /**
     * 
     * @param eventListener the {@link EventListener}
     */
    protected void registerListener(final EventListener<? extends Event> eventListener) {
        this.getEntity().registerListener(eventListener);
        this.eventListeners.add(eventListener);
    }

    /**
     * Register all event listener of this component.
     */
    public void registerAllListener() {
        this.eventListeners.forEach(eLis -> this.getEntity().registerListener(eLis));
    }

    /**
     * Unregister all event listener of this component.
     */
    public void unregisterAllListener() {
        this.eventListeners.forEach(eLis -> this.getEntity().unregisterListener(eLis));
    }

    /**
     * Get the entity this component is attached to.
     * 
     * @return {@link Entity}.
     */
    public Entity getEntity() {
        return this.entity;
    }

    /**
     * Sets the entity this component has to be attached to.
     * 
     * @param e {@link Entity}
     */
    protected final void setEntity(final Entity e) {
        if (this.entity != null) {
            throw new IllegalStateException();
        }
        this.entity = e;
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
