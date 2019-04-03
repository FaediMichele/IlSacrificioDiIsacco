package model.entity;

import model.component.Component;

/**
 * The entity for the tears, they are the main damage dealing entity.
 */
public class Tear extends AbstractMovableEntity {

  /**
   * @param entityBody the Body
   * @param entityCollision the collision component
   */
  public Tear(final Component entityBody, final Component entityCollision) {
    super(entityBody, entityCollision);
  }
}
