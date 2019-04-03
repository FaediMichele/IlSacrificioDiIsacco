package model.component;

import model.entity.Entity;

/**
 * Generic fields and methods neeeded by each component.
 *
 */

public abstract class AbstractComponent implements Component {
    private boolean active;
    private Entity entity;

    @Override
    public final boolean isActive() {
      return active;
    }

    @Override
    public final void setState(final boolean state) {
      active = state;
    }

    @Override
    public final Entity getEntity() {
      return entity;
    }

    /**
     * Sets the entity this component has to be attached to. It can be changed only by the component itself.
     * @param e {@link Entity}
     */
    protected void setEntity(final Entity e) {
      if (entity != null) {
        throw new IllegalStateException();
      }
      entity = e;
    }

    @Override
    public final void dispose() {
      active = false;
    }

}
