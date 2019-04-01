package model.entity;

import model.component.Component;

/**
 * Implements the doors.
 */
public class Door extends AbstractEntity {
  /**
   * 
   * @param entityPosition the entity position
   * @param entityCollision the collision component
   */
  public Door(final Component entityPosition, final Component entityCollision) {
    super(entityPosition, entityCollision);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "Door";
  }
}
