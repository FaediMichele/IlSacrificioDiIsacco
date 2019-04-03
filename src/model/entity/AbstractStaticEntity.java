package model.entity;

import model.component.Component;

/**
 * Base class for all the static entities such as rocks and doors.
 * See also {@link AbstractEntity}.
 */
public class AbstractStaticEntity extends AbstractEntity {

  /**
   * 
   * @param entityPosition the position
   * @param entityCollision the collision
   */
  public AbstractStaticEntity(final Component entityPosition, final Component entityCollision) {
    super(entityPosition, entityCollision);
    // TODO Auto-generated constructor stub
  }

}
