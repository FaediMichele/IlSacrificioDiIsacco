package model.entity;

import model.component.BodyComponent;
import model.component.Component;

/**
 * Implements the fires.
 */
public class Fire extends AbstractStaticEntity {

  /**
   * @param entityBody the entity Body
   * @param entityCollision the collision component
   */
  public Fire(final BodyComponent entityBody, final Component entityCollision) {
    super(entityBody, entityCollision);
  }
}
