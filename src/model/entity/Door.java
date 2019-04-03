package model.entity;

import model.component.Component;

/**
 * Implements the doors.
 */
public class Door extends AbstractStaticEntity {

  /**
   * @param entityBody the entity Body
   * @param entityCollision the collision component
   */
  public Door(final Component entityBody, final Component entityCollision) {
    super(entityBody, entityCollision);
  }
}
