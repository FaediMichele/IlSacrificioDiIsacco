package model.component;

import model.entity.Entity;

/**
 * Generic fields and methods needed by each component.
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

    @Override
    public final void setEntity(final Entity e) {
      if (entity != null) {
        throw new IllegalStateException();
      }
      entity = e;
    }

    @Override
    public final void dispose() {
      active = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Double deltaTime) {

    }
}
