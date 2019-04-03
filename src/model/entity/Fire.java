package model.entity;

import model.component.Component;

/**
 * Implements the fires.
 */
public class Fire extends AbstractStaticEntity {

  /**
   * 
   * @param entityPosition the entity position
   * @param entityCollision the collision component
   */
  public Fire(final Component entityPosition, final Component entityCollision) {
    super(entityPosition, entityCollision);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "Fire";
  }

}
