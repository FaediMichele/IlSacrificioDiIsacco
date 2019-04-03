package model.entity;

import model.component.Component;

/**
 * The entity for the tears, they are the main damage dealing entity.
 */
public class Tear extends AbstractMovableEntity {

  /**
   * {@inheritDoc}.
   * @param entityPosition the position
   * @param entityCollision the collision component
   */
  public Tear(final Component entityPosition, final Component entityCollision) {
    super(entityPosition, entityCollision);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "Tear";
  }
}
