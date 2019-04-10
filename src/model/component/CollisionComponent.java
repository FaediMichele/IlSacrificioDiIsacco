package model.component;

import model.entity.Entity;

/**
 * This class manages the collision of this entity with the others.
 *
 */

public class CollisionComponent extends AbstractComponent {

    /**
     * Default CollisionComponent constructor.
     * @param e entity for this component
     */
    public CollisionComponent(final Entity e) {
        super(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
