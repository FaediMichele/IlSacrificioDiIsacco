package model.entity;

import model.component.Component;

/**
 * Base class for all the movable entities.
 * See also {@link AbstractEntity}.
 */
public abstract class AbstractMovableEntity extends AbstractEntity {

  /**
   * 
   * @param entityBody the Body
   * @param entityCollision the collision
   */
  public AbstractMovableEntity(final Component entityBody, final Component entityCollision) {
    super(entityBody, entityCollision);
  }

}
