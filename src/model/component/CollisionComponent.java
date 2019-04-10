package model.component;

import model.entity.Entity;

/**
 * This class manages the collision of this entity with the others.
 *
 */

public class CollisionComponent extends AbstractComponent {

    /**
     * Default CollisionComponent constructor.
     * 
     * @param e entity for this component
     */
    public CollisionComponent(final Entity e) {
        super(e);
    }


    @Override
    public final int hashCode() {
        return super.hashCode();
    }


    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof CollisionComponent)) {
            return false;
        }
        return true;
    }





}
