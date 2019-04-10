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
        this.componentReplaced = Optional.empty();
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
    public int hashCode() {
        final int prime = 31;
        final int second = 1231;
        final int third = 1237;
        int result = 1;
        result = prime * result + (active ? second : third);
        result = prime * result + (!(componentReplaced.isPresent()) ? 0 : componentReplaced.get().hashCode());
        result = prime * result + ((entity == null) ? 0 : entity.hashCode());
        return result;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractComponent other = (AbstractComponent) obj;
        if (active != other.active) {
            return false;
        }
        if (componentReplaced == null) {
            if (other.componentReplaced != null) {
                return false;
            }
        } else if (!componentReplaced.equals(other.componentReplaced)) {
            return false;
        }
        if (entity == null) {
            if (other.entity != null) {
                return false;
            }
        } else if (!entity.equals(other.entity)) {
            return false;
        }
        return true;
    }
}
