package model.entity;

import model.component.BodyComponent;
import model.component.Component;

/**
 * Implements Isaac.
 */
public class Isaac extends AbstractMovableEntity {

  /**
   * @param entityBody the entity Body
   * @param entityCollision the collision component
   */
  public Isaac(final BodyComponent entityBody, final Component entityCollision) {
    super(entityBody, entityCollision);
  }
}
