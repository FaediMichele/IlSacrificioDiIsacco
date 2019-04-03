package model.entity;

import model.component.BodyComponent;
import model.component.Component;

/**
 * Base class for all the static entities such as rocks and doors.
 * See also {@link AbstractEntity}.
 */
public class AbstractStaticEntity extends AbstractEntity {

  /**
   * 
   * @param entityBody the Body
   * @param entityCollision the collision
   */
  public AbstractStaticEntity(final BodyComponent entityBody, final Component entityCollision) {
    super(entityBody, entityCollision);
    // TODO Auto-generated constructor stub
  }

}
