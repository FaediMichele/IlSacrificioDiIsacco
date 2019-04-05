package model.game;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import model.component.HealthComponent;
import model.entity.Door;
import model.entity.Entity;

/**
 * This class is the implementation for the room.
 *
 */
public class RoomImpl implements Room {

  private final ArrayList<? extends Entity> entity = new ArrayList<>();
  private final ArrayList<? extends Door> door = new ArrayList<>();
  private boolean isComplete = false;

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<? extends Entity> getEntity() {
    return new LinkedHashSet<Entity>(entity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<? extends Door> getDoor() {
    return new LinkedHashSet<Door>(door);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateEntity(final Double deltaTime) {
    entity.forEach(e -> e.update(deltaTime));
    if (entity.stream().filter(e -> e.hasComponent(HealthComponent.class)).
        filter(e -> ((HealthComponent) e.getComponent(HealthComponent.class).get())
                .isAlive()).count() == 0) {
      //entity.add(new Bomb());
      isComplete = true;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isComplete() {
    return isComplete;
  }

}
