package model.component;

import java.util.Optional;

import model.entity.Entity;

/**
 * Generic fields and methods needed by each component.
 *
 */

public abstract class AbstractComponent implements Component {
    private boolean active;
    private Entity entity;
    private Optional<Component> componentReplaced;

    AbstractComponent(final Entity entity) {
        this.entity = entity;
    }
    /**
     * Check if the component is active, this has to be done before each update call.
     * @return {@link Boolean}.
     */
    protected final boolean isActive() {
      return active;
    }

    /**
     * Enable or disable a component by setting a variable. 
     * @param state {@link Boolean}.
     */
    protected final void setState(final boolean state) {
      active = state;
    }

    /**
     * Get the entity this component is attached to.
     * @return {@link Entity}.
     */
    protected final Entity getEntity() {
      return entity;
    }

    /**
     * Sets the entity this component has to be attached to.
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
      active = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Double deltaTime) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object component) {
        return false;
    }
}
