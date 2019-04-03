package model.entity;

import model.component.Component;

/**
 * Implements the fires.
 */
public class Fire extends AbstractStaticEntity {

  /**
   * @param entityBody the entity Body
   * @param entityCollision the collision component
   */
  public Fire(final Component entityBody, final Component entityCollision) {
    super(entityBody, entityCollision);
  }
}
