package model.entity;

import model.component.Component;

/**
 * Implements Isaac.
 */
public class Isaac extends AbstractMovableEntity {

  /**
   * 
   * @param entityPosition the entity position
   * @param entityCollision the collision component
   */
  public Isaac(final Component entityPosition, final Component entityCollision) {
    super(entityPosition, entityCollision);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "Isaac";
  }
}
