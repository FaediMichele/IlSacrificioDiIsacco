package model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.component.BodyComponent;
import model.component.HealthComponent;
import model.component.ObstacleComponent;
import model.component.StatusComponent;
import model.entity.Door;
import model.entity.Entity;
import model.events.CollisionEvent;
import model.events.DeadEvent;
import model.util.EntityInformation;
import util.EventListener;
import util.NotEquals;
import util.NotHashCode;
import util.Pair;
import util.Space;
import util.StaticMethodsUtils;

/**
 * This class is the implementation for the room.
 *
 */
public class RoomImpl implements Room {
    private static final int NORD = 0;
    private static final int EAST = 1;
    private static final int SUD = 2;
    private static final int OVEST = 3;

    private final List<Entity> entity;
    private final List<Entity> graveyard = new ArrayList<Entity>();
    private final List<Door> doors;
    private final int index;
    @NotEquals
    @NotHashCode
    private final Space sp = new Space();
    @NotEquals
    @NotHashCode
    private final Map<Entity, Space.Rectangle> entityRectangleSpace = new TreeMap<>(
            (a, b) -> a.hashCode() - b.hashCode());
    @NotEquals
    @NotHashCode
    private final Map<Space.Rectangle, Entity> rectangleEntitySpace = new TreeMap<>(
            (a, b) -> a.hashCode() - b.hashCode());
    private Floor floor;
    private boolean isComplete;
    private boolean cleanGraveyard;

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
        this.entity = new ArrayList<>();
        entity.forEach(e -> insertEntity(e));
        cleanGraveyard = false;
    }

    /**
     * Initialize a {@link Room} via xml.
     * 
     * @param roomName the room name in xml
     * @param index    the index of the room
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public RoomImpl(final String roomName, final int index)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        final Document docXML = StaticMethodsUtils.getDocumentXML("/xml/Room.xml");
        final List<Node> ls = StaticMethodsUtils
                .getNodesFromNodelList(docXML.getElementsByTagName(roomName).item(0).getChildNodes());
        final Optional<Node> node = ls.stream().filter(n -> n.getNodeName().equals("Entities")).findFirst();
        this.entity = new ArrayList<Entity>();
        this.doors = new ArrayList<Door>();
        this.index = index;
        this.cleanGraveyard = false;
        if (node.isPresent()) {
            final NodeList nl = node.get().getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                final Node entity = nl.item(i);
                if (entity.getNodeType() == Node.ELEMENT_NODE) {
                    final String entityName = entity.getNodeName();
                    if (entityName.equals("Door")) {
                        switch (entity.getTextContent()) {
                        case "RIGHT":
                            this.doors.add(new Door(EAST, this.index));
                            break;
                        case "DOWN":
                            this.doors.add(new Door(SUD, this.index));
                            break;
                        case "LEFT":
                            this.doors.add(new Door(OVEST, this.index));
                            break;
                        default:
                            this.doors.add(new Door(NORD, this.index));
                            break;
                        }
                    } else {
                        final Entity e = (Entity) Class.forName("model.entity." + entityName).newInstance();
                        final Double x = Double.parseDouble(entity.getAttributes().getNamedItem("x").getNodeValue());
                        final Double y = Double.parseDouble(entity.getAttributes().getNamedItem("y").getNodeValue());
                        final Double z = Double.parseDouble(entity.getAttributes().getNamedItem("z").getNodeValue());
                        e.getComponent(BodyComponent.class).get().changePosition(x, y, z);
                        this.entity.add(e);
                    }
                }
            }
        }
    }

    private Space.Rectangle getShape(final Entity e) {
        final BodyComponent b = e.getComponent(BodyComponent.class).get();
        return new Space.Rectangle(b.getPosition().getX(), b.getPosition().getY(), b.getWidth(), b.getHeight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Entity> getEntitys() {
        return Collections.unmodifiableList(this.entity);
    }

    /**
     * {@inheritDoc}
     * 
     * @return
     */
    @Override
    public List<EntityInformation> getEntitysStatus() {
        return this.entity.stream()
                .map(e -> new EntityInformation().setEntity(e.getNameEntity()).setId(e.getId())
                        .setHeight(e.getComponent(BodyComponent.class).get().getHeight())
                        .setWidth(e.getComponent(BodyComponent.class).get().getWidth())
                        .setMove(e.getComponent(StatusComponent.class).get().getMove())
                        .setStatus(e.getComponent(StatusComponent.class).get().getStatus())
                        .setPosition(e.getComponent(BodyComponent.class).get().getPosition())
                        .setUpgrade(e.getComponent(StatusComponent.class).get().getUpgrade()))
                .collect(Collectors.toList());
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
                .filter(e -> (e.getComponent(HealthComponent.class).get()).isAlive()).count() == 1) {
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
        final Entity tmp1 = e1, tmp2 = e2;
        e1.postEvent(new CollisionEvent(tmp2));
        e2.postEvent(new CollisionEvent(tmp1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Pair<Entity, Entity>> getEntityColliding() {
        return sp.getCollisions().stream().map(
                p -> new Pair<Entity, Entity>(rectangleEntitySpace.get(p.getX()), rectangleEntitySpace.get(p.getY())))
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
                final BodyComponent b = e.getComponent(BodyComponent.class).get();
                r.setX(b.getPosition().getX());
                r.setY(b.getPosition().getY());
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
        sp.addRectangle(r, !e.hasComponent(ObstacleComponent.class));
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

    @Override
    public final boolean equals(final Object obj) {
        return StaticMethodsUtils.equals(this, obj);
    }

    @Override
    public final int hashCode() {
        return StaticMethodsUtils.hashCode(this);
    }
}
