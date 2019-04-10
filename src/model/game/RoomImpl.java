package model.game;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import model.component.HealthComponent;
import model.entity.Door;
import model.entity.Entity;

/**
 * This class is the implementation for the room.
 *
 */
public class RoomImpl implements Room {

    private final List<Entity> entity;
    private final List<? extends Door> doors;
    private boolean isComplete;
    private final int index;

    /**
     * Create a room with door and entity.
     * @param index the index of the room
     * @param door the door of this room
     * @param entity the entity of this room
     */
    public RoomImpl(final int index, final List<Door> door, final List<Entity> entity) {
        this.index = index;
        this.doors = door;
        isComplete = false;
        this.entity = entity; 
    }

    /**
     * Create a room with only door. Entity is calculated based on the door.
     * @param index the index of the room
     * @param doors the door of this room
     */
    public RoomImpl(final int index, final List<Door> doors) {
        this.index = index;
        this.doors = doors;
        entity = new ArrayList<>();
    }
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
        return new LinkedHashSet<Door>(doors);
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
    public boolean completed() {
        return isComplete;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIndex() {
        return index;
    }

    /**
     * {@inheritDoc}
     */
    public void insertEntity(final Entity e) {
        entity.add(e);
    }
}
