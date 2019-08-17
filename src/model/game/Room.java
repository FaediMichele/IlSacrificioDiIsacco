package model.game;

import java.util.List;
import java.util.Set;
import model.entity.Door;
import model.entity.Entity;
import model.util.EntityInformation;
import util.Pair;

/**
 * The interface for the rooms for the floor. It have enemy and environment
 * entity.
 */
public interface Room {
    /**
     * Get all the {@link Entity} that the room contains.
     * 
     * @return Set of all {@link Entity}.
     */
    List<? extends Entity> getEntities();

    /**
     * Get a list of the status of all the entities. Get also the deleted entities
     * if not already got before then delete them.
     * 
     * @return the map from {@link KeyMapStatusEnum} to {@link ValuesMapStatusEnum}.
     */
    List<EntityInformation> getEntitiesStatus();

    /**
     * Get the door that the room have.
     * 
     * @return Set of the Door.
     */
    Set<? extends Door> getDoor();

    /**
     * Update all the {@link Entity} (run a frame).
     * 
     * @param deltaTime time that has passed between the last update.
     */
    void updateEntity(Double deltaTime);

    /**
     * Insert or delete the entity to the room that needs to be added or removed.
     */
    void updateEntityList();

    /**
     * React to the collision that is found at this time.
     */
    void calculateCollision();

    /**
     * Get the {@link Entity} that is colliding.
     * 
     * @return the {@link Entity} that collide.
     */
    Set<Pair<Entity, Entity>> getEntityColliding();

    /**
     * If this room has been completed (the player killed all the enemy).
     * 
     * @return true if the room is completed.
     */
    boolean completed();

    /**
     * Get the index of the room.
     * 
     * @return the index of the room.
     */
    int getIndex();

    /**
     * Set the name of the room in order to load the entities.
     * 
     * @param name the name of the room.
     */
    void fill(String name);

    /**
     * Add an {@link Entity} to the room.
     * 
     * @param e the {@link Entity}
     * @return this
     */
    Room insertEntity(Entity e);

    /**
     * Delete the {@link Entity} to the room.
     * 
     * @param e the {@link Entity} to delete
     */
    void deleteEntity(Entity e);

    /**
     * Get the {@link Floor} that contains the room.
     * 
     * @return the {@link Floor} where the room is.
     */
    Floor getFloor();

    /**
     * Set the {@link Floor} that contains the room.
     * 
     * @param f the {@link Floor} where the room is
     */
    void setFloor(Floor f);

    /**
     * Get the shortest path. Be careful to not use this every frame because it
     * cost.
     * 
     * @param start the entity to start the route.
     * @param dest  the entity of destination.
     * @return a near point to the preferred path.
     */
    Pair<Double, Double> getRoute(Entity start, Entity dest);

    /**
     * @return the width
     */
    double getWidth();

    /**
     * @return the height
     */
    double getHeight();
}
