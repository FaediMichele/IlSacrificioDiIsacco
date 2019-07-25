package model.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.component.BodyComponent;
import model.component.HealthComponent;
import model.component.StatusComponent;
import model.entity.Door;
import model.entity.Entity;
import model.events.CollisionEvent;
import model.events.DeadEvent;
import util.EventListener;
import util.Pair;
import util.Space;
import util.enumeration.KeyMapStatusEnum;
import util.enumeration.ValuesMapStatusEnum;

/**
 * This class is the implementation for the room.
 *
 */
public class RoomImpl implements Room {

    private final List<Entity> entity;
    private final List<Entity> graveyard = new ArrayList<Entity>();
    private boolean cleanGraveyard;
    private final List<? extends Door> doors;
    private boolean isComplete;
    private final int index;
    private Floor floor;
    private final Space sp = new Space();
    private final Map<Entity, Space.Rectangle> entityRectangleSpace = new LinkedHashMap<>();
    private final Map<Space.Rectangle, Entity> rectangleEntitySpace = new LinkedHashMap<>();

    /**
     * Create a room with only door. Entity is calculated based on the door.
     * 
     * @param index the index of the room
     * @param doors the door of this room
     */
    public RoomImpl(final int index, final List<Door> doors) {
        this.index = index;
        this.doors = doors;
        this.entity = new ArrayList<>();
        cleanGraveyard = false;
    }

    /**
     * Create a room with door and entity.
     * 
     * @param index  the index of the room
     * @param door   the door of this room
     * @param entity the entity of this room
     */
    public RoomImpl(final int index, final List<Door> door, final List<Entity> entity) {
        this.index = index;
        this.doors = door;
        this.isComplete = false;
        this.entity = entity;
        this.entity.forEach(e -> {
            final Space.Rectangle r = getShape(e);
            entityRectangleSpace.put(e, r);
            rectangleEntitySpace.put(r, e);
            sp.addRectangle(r);
            e.changeRoom(this);
            addEventEntity(e);
        });
        cleanGraveyard = false;
    }
    private Space.Rectangle getShape(final Entity e) {
        final BodyComponent b = (BodyComponent) e.getComponent(BodyComponent.class).get();
        return new Space.Rectangle(b.getPosition().getV1(), b.getPosition().getV2(), b.getWidth(), b.getHeight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends Entity> getEntity() {
        return new LinkedHashSet<Entity>(this.entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<KeyMapStatusEnum, ValuesMapStatusEnum> getEntitysStatus() {
        final Stream<Map<KeyMapStatusEnum, ValuesMapStatusEnum>> e = entity.stream().map(Entity::getStatusComponent).map(StatusComponent::getStatus);
        final Stream<Map<KeyMapStatusEnum, ValuesMapStatusEnum>> g = graveyard.stream().map(Entity::getStatusComponent).map(StatusComponent::getStatus);
        cleanGraveyard = true;
        return Stream.concat(e, g).flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(en -> (KeyMapStatusEnum) en.getKey(),  ent -> (ValuesMapStatusEnum) ent.getValue()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<? extends Door> getDoor() {
        return new LinkedHashSet<Door>(this.doors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEntity(final Double deltaTime) {
        this.entity.forEach(e -> e.update(deltaTime));
        if (this.entity.stream().filter(e -> e.hasComponent(HealthComponent.class))
                .filter(e -> ((HealthComponent) e.getComponent(HealthComponent.class).get()).isAlive()).count() == 1) {
            this.isComplete = true;
        }
    }

    @Override
    public final void calculateCollision() {
        updateSpace();
        // get the collision detected and for each one call the event. 
        getEntityColliding().forEach(p -> postCollision(p.getX(), p.getY()));
    }
    private void postCollision(final Entity e1, final Entity e2) {
        e1.postEvent(new CollisionEvent(e2));
        e2.postEvent(new CollisionEvent(e1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Pair<Entity, Entity>> getEntityColliding() {
        return sp.getCollisions().stream()
                .map(p -> new Pair<Entity, Entity>(rectangleEntitySpace.get(p.getX()), rectangleEntitySpace.get(p.getY())))
                .collect(Collectors.toSet());
    }

    /**
     * Update the rectangle in the space.
     */
    private void updateSpace() {
        this.entity.forEach(e -> {
            final Space.Rectangle r = entityRectangleSpace.get(e);
            if (r == null) { 
                // Should never come here.
                final Space.Rectangle rtmp = getShape(e);
                entityRectangleSpace.put(e, rtmp);
                rectangleEntitySpace.put(rtmp, e);
                sp.addRectangle(rtmp); 
            } else {
                final BodyComponent b = (BodyComponent) e.getComponent(BodyComponent.class).get();
                r.setX(b.getPosition().getV1());
                r.setY(b.getPosition().getV2());
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean completed() {
        return this.isComplete;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIndex() {
        return this.index;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEntity(final Entity e) {
        this.entity.add(e);
        final Space.Rectangle r = getShape(e);
        entityRectangleSpace.put(e, r);
        rectangleEntitySpace.put(r, e);
        sp.addRectangle(r);
        e.changeRoom(this);
        addEventEntity(e);
    }
    private void addEventEntity(final Entity e) {
        e.registerListener(new EventListener<DeadEvent>() {
            @Override
            public void listenEvent(final DeadEvent event) {
                entity.remove(e);
                if (cleanGraveyard) {
                    graveyard.clear();
                    cleanGraveyard = false;
                }
                graveyard.add(e);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEntity(final Entity e) {
        this.entity.remove(e);
        sp.remove(entityRectangleSpace.get(e));
        rectangleEntitySpace.remove(entityRectangleSpace.get(e));
        entityRectangleSpace.remove(e);
        e.changeRoom(null);
    }

    @Override
    public final Floor getFloor() {
        return this.floor;
    }

    @Override
    public final void setFloor(final Floor f) {
        if (this.floor != null) {
            throw new IllegalStateException("The room cannot change the floor");
        }
        this.floor = f;
    }

    /**
     * {@inheritDoc} .
     */
    @Override
    public Pair<Double, Double> getRoute(final Entity start, final Entity dest) {
        return sp.getNextNodePath(entityRectangleSpace.get(start), entityRectangleSpace.get(dest));
    }
}
