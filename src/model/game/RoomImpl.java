package model.game;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import model.entity.Entity;

/**
 * This class is the implementation for the room.
 *
 */
public class RoomImpl implements Room {

  private final ArrayList<Entity> entity = new ArrayList<>();
  private final ArrayList<Entity> door = new ArrayList<>();
  private boolean isActive = false;
  private boolean isComplete = false;

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<Entity> getEntity() {
    return new LinkedHashSet<Entity>(entity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<Entity> getDoor() {
    return new LinkedHashSet<Entity>(door);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateEntity(final Double deltaTime) {
    entity.forEach(e -> e.update(deltaTime));
    /* Wait till the merge
    if (entity.stream().filter(e -> e.has(LifeComponent.class)).
        filter(e -> e.get(LifeComponent.class).isAlive()).count() == 0) {
      entity.add(new Bomb());
      isComplete = true;
    }
    */
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isActive() {
    return isActive;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setActive(final boolean state) {
    isActive = state;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isComplete() {
    return isComplete;
  }

}
