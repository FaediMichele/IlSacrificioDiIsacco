package model.entity;

import model.component.Component;

/**
 * Base class for all the movable entities.
 * See also {@link AbstractEntity}.
 */
public abstract class AbstractMovableEntity extends AbstractEntity {

  /**
   * 
   * @param entityPosition the position
   * @param entityCollision the collision
   */
  public AbstractMovableEntity(final Component entityPosition, final Component entityCollision) {
    super(entityPosition, entityCollision);
  }

}
