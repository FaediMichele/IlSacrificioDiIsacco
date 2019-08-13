package model.component;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import model.entity.Entity;
import model.events.Event;
import util.EqualsForGetters;
import util.EventListener;
import util.StaticMethodsUtils;

/**
 * Generic fields and methods needed by each component.
 * 
 */
public abstract class AbstractComponent implements Component {
    private boolean active;
    private Entity entity;
    private final List<EventListener<? extends Event>> eventListeners;


    /**
     * This is the father of all the components.
     * @param entity to which this component belongs
     */
    protected AbstractComponent(final Entity entity) {
        Objects.requireNonNull(entity);
        this.entity = entity;
        this.eventListeners = new LinkedList<EventListener<? extends Event>>();
        this.active = true;
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
        this.eventListeners.forEach(eLis -> this.getEntity().unregisterListener(eLis));
    }

    /**
     * Check if the component is active, this has to be done before each update
     * call.
     * 
     * @return {@link Boolean}.
     */
    @EqualsForGetters
    public final boolean isActive() {
        return this.active;
    }

    /**
     * Enable or disable a component by setting a variable.
     * 
     * @param state {@link Boolean}.
     */
    public final void setState(final boolean state) {
        this.active = state;
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
