package model.entity;

import model.component.BodyComponent;
import model.component.Component;

/**
 * Implements the doors.
 */
public class Door extends AbstractStaticEntity {

  /**
   * @param entityBody the entity Body
   * @param entityCollision the collision component
   */
  public Door(final BodyComponent entityBody, final Component entityCollision) {
    super(entityBody, entityCollision);
  }
}
