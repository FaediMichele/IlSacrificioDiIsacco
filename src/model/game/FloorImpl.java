package model.game;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.eventbus.EventBus;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.component.BodyComponent;
import model.entity.Door;
import model.entity.Entity;
import model.entity.Player;
import model.entity.Wall;
import model.enumeration.BasicMovementEnum;
import model.events.RoomChangedEvent;
import util.EventListener;
import util.Matrix;
import util.NotEquals;
import util.NotHashCode;
import util.Pair;
import util.StaticMethodsUtils;
import view.javafx.ViewGetterUtil;

/**
 * Implementation of Floor.
 * 
 * <p>
 * See also {@link Room}
 *
 */
public class FloorImpl implements Floor {
    private Pane pn;
    private static final int NORD = 0;
    private static final int EAST = 1;
    private static final int SUD = 2;
    private static final int OVEST = 3;

    private final List<Room> rooms;
    @NotEquals
    @NotHashCode
    private final EventBus eventBus = new EventBus();
    private int activeRoomIndex;
    private boolean changedRoom;
    private int maxRoom;

    /**
     * Generate a random floor with random room and random enemy.
     */
    public FloorImpl() {
        this.rooms = new ArrayList<>();
        this.activeRoomIndex = 0;
        changedRoom = false;
    }

    /**
     * Create a floor with specific rooms.
     * 
     * @param rooms rooms to use for this floor
     */
    public FloorImpl(final List<Room> rooms) {
        this.rooms = new ArrayList<>(rooms);
        this.rooms.forEach(r -> r.setFloor(this));
        changedRoom = false;
    }

    /**
     * Create the {@link Floor} via xml.
     * 
     * @param floorName the floor name in xml
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public FloorImpl(final String floorName)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        final Document docXML = StaticMethodsUtils.getDocumentXML("/xml/Floor.xml");
        final List<Node> ls = StaticMethodsUtils
                .getNodesFromNodelList(docXML.getElementsByTagName(floorName).item(0).getChildNodes());
        maxRoom = Integer.parseInt(
                docXML.getElementsByTagName(floorName).item(0).getAttributes().getNamedItem("MaxRoom").getNodeValue());
        this.rooms = new ArrayList<>();
        generateRooms();
        final Optional<Node> node = ls.stream().filter(n -> n.getNodeName().equals("Rooms")).findFirst();
        this.activeRoomIndex = 0;
        final Random rnd = new Random();
        if (node.isPresent()) {
            final NodeList nl = node.get().getChildNodes();
            for (int i = 0; i < rooms.size(); i++) {
                final int n = nl.getLength();
                final int rndIndex = rnd.nextInt((n - 1) / 2);
                final Node room = nl.item(rndIndex * 2 + 1);
                if (room.getNodeType() == Node.ELEMENT_NODE) {
                    final String roomName = room.getNodeName();
                    rooms.get(i).fill(roomName);
                }
            }
        }
        changedRoom = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Room getActiveRoom() {
        return this.rooms.get(this.activeRoomIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeRoom(final Integer index) {
        if (this.rooms.stream().filter(r -> r.getIndex() == index).count() != 1) {
            throw new IllegalArgumentException("Room not found");
        }
        this.activeRoomIndex = index;
        changedRoom = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Room> getRooms() {
        return new LinkedHashSet<>(this.rooms);
    }

    private void generateRooms() {
        if (!this.rooms.isEmpty()) {
            throw new IllegalStateException("Floor already created");
        }
        final Matrix<Integer> m = new Matrix<>(maxRoom, maxRoom);
        final List<Pair<Integer, Integer>> roomIndexs = new ArrayList<>();
        generateMap(m, roomIndexs);
        final double widthRoom = 489;
        final double heightRoom = 224;
        for (int index = 0; index < roomIndexs.size(); index++) {
            this.rooms.add(createEmptyRoom(index, m, roomIndexs.get(index), widthRoom, heightRoom));
        }
    }

    /**
     * Generate an empty room based on activeRoomIndex and adjacent room.
     * 
     * @param activeRoomIndex the activeRoomIndex of the room
     * @param m               Matrix of adjacent room
     * @param pos             the position on the matrix of the room to initialize
     * @param width           the width of the room
     * @param height          the height of the room
     * @return the room
     */
    private Room createEmptyRoom(final int index, final Matrix<Integer> m, final Pair<Integer, Integer> pos,
            final double width, final double height) {
        final Room r = new RoomImpl(index, width, height);
        final List<Door> doors = new ArrayList<>();
        final List<Wall> walls = new ArrayList<>();
        final Pair<Double, Double> roomSize = new Pair<Double, Double>(r.getWidth(), r.getHeight());
        if (this.roomExist(m, pos.getX(), pos.getY() - 1)) { // NORD
            doors.add(new Door(BasicMovementEnum.UP, index, m.get(pos.getX(), pos.getY() - 1), roomSize));
        }
        if (this.roomExist(m, pos.getX() + 1, pos.getY())) { // EAST
            doors.add(new Door(BasicMovementEnum.RIGHT, index, m.get(pos.getX() + 1, pos.getY()), roomSize));
        }
        if (this.roomExist(m, pos.getX(), pos.getY() + 1)) { // SUD
            doors.add(new Door(BasicMovementEnum.DOWN, index, m.get(pos.getX(), pos.getY() + 1), roomSize));
        }
        if (this.roomExist(m, pos.getX() - 1, pos.getY())) { // OVEST
            doors.add(new Door(BasicMovementEnum.LEFT, index, m.get(pos.getX() - 1, pos.getY()), roomSize));
        }
        walls.add(new Wall(BasicMovementEnum.UP, roomSize));
        walls.add(new Wall(BasicMovementEnum.RIGHT, roomSize));
        walls.add(new Wall(BasicMovementEnum.DOWN, roomSize));
        walls.add(new Wall(BasicMovementEnum.LEFT, roomSize));
        doors.forEach(d -> r.insertEntity(d));
        //doors.forEach(d -> d.changeRoom(r));

        walls.forEach(d -> r.insertEntity(d));
        //walls.forEach(d -> d.changeRoom(r));
        r.setFloor(this);
        return r;
    }

    /**
     * Generate the map.
     * 
     * @param m          {@link Matrix} where to save the room ( used for nearby
     *                   rooms)
     * @param roomIndexs {@link ArrayList} of positions of each room in increasing
     *                   order
     */
    private void generateMap(final Matrix<Integer> m, final List<Pair<Integer, Integer>> roomIndexs) {
        final Pair<Integer, Integer> pos = new Pair<>(maxRoom / 2, maxRoom / 2);
        final Random rnd = new Random();
        final int nRoom = rnd.nextInt(maxRoom / 2) + maxRoom / 2;

        for (int index = 0; index < nRoom; index++) {
            int direction = rnd.nextInt(OVEST + 1);
            int directionCounted = 0;
            while (!canGoDirection(m, pos, direction) && directionCounted <= OVEST) {
                direction = (direction + 1) % OVEST;
                directionCounted++;
            }
            if (directionCounted == OVEST) { // No other direction is possible
                index = nRoom;
            } else {
                this.updatePosition(pos, direction);
                m.set(pos.getX(), pos.getY(), index);
                roomIndexs.add(new Pair<Integer, Integer>(pos.getX(), pos.getY()));
            }
        }
    }

    /**
     * Says if the next room can be in the direction.
     * 
     * @param m         the matrix
     * @param posX      the actual X
     * @param posY      the actual Y
     * @param direction the direction to verify (0= Nord; 1= East; 2= Sud; 3= Ovest)
     * @return if the next room can be in the direction
     */
    private boolean canGoDirection(final Matrix<?> m, final Pair<Integer, Integer> pos, final int direction) {
        switch (direction) {
        case NORD:
            return pos.getY() > 0 && m.get(pos.getX(), pos.getY() - 1) == null;
        case EAST:
            return pos.getX() < m.getWidth() - 1 && m.get(pos.getX() + 1, pos.getY()) == null;
        case SUD:
            return pos.getY() < m.getHeight() - 1 && m.get(pos.getX(), pos.getY() + 1) == null;
        case OVEST:
            return pos.getX() > 0 && m.get(pos.getX() - 1, pos.getY()) == null;
        default:
            throw new IllegalArgumentException();
        }
    }

    private boolean roomExist(final Matrix<Integer> m, final int posX, final int posY) {
        return posX >= 0 && posX < m.getWidth() && posY >= 0 && posY < m.getHeight() && m.get(posX, posY) != null;
    }

    /**
     * Update the position based on the direction.
     * 
     * @param pos       the current position
     * @param direction the direction (0= Nord; 1= Est; 2= Sud; 3= Ovest)
     */
    private void updatePosition(final Pair<Integer, Integer> pos, final int direction) {
        switch (direction) {
        case NORD:
            pos.setY(pos.getY() - 1);
            break;
        case EAST:
            pos.setX(pos.getX() + 1);
            break;
        case SUD:
            pos.setY(pos.getY() + 1);
            break;
        case OVEST:
            pos.setX(pos.getX() - 1);
            break;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Double deltaTime) {
        getActiveRoom().updateEntity(deltaTime);
        changedRoom = false;
        getActiveRoom().calculateCollision();
        getActiveRoom().updateEntityList();
        debug();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void changeEntityRoom(final Entity e, final Integer location, final Integer destination) {
        if (e.getClass().equals(Player.class)) {
            eventBus.post(new RoomChangedEvent(rooms.get(location), rooms.get(destination)));
            changedRoom = true;
            activeRoomIndex = destination;
        }
        this.rooms.get(location).deleteEntity(e);
        this.rooms.get(location).updateEntityList();
        this.rooms.get(destination).insertEntity(e);
        this.rooms.get(destination).updateEntityList();
        
    }

    @Override
    public final void registerListener(final EventListener<?> eventListener) {
        this.eventBus.register(eventListener);
    }

    @Override
    public final void unregisterListener(final EventListener<?> eventListener) {
        this.eventBus.unregister(eventListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isChangeRoom() {
        return changedRoom;
    }

    @Override
    public final boolean equals(final Object obj) {
        return StaticMethodsUtils.equals(this, obj);
    }

    @Override
    public final int hashCode() {
        return StaticMethodsUtils.hashCode(this);
    }
    private void debug() {
        if (pn == null) {
            pn = new Pane();
            Platform.runLater(() -> ((Pane) ViewGetterUtil.getScene().getRoot()).getChildren().add(pn));
        }
        Platform.runLater(() -> pn.getChildren().clear());

        final Rectangle back = new Rectangle();
        back.setX(0.0);
        back.setY(0.0);
        back.setWidth(getActiveRoom().getWidth());
        back.setHeight(getActiveRoom().getHeight());
        back.setFill(Color.TRANSPARENT);
        back.setStroke(Color.WHITE);
        Platform.runLater(() -> pn.getChildren().add(back));
        getActiveRoom().getDoor().forEach(e -> {
            final BodyComponent b = e.getComponent(BodyComponent.class).get();
            final Rectangle r = new Rectangle();
            r.setX(b.getPosition().getX());
            r.setY(b.getPosition().getY());
            r.setWidth(b.getWidth());
            r.setHeight(b.getHeight());
            r.setStroke(Color.BLUE);
            r.setFill(Color.BLUE);
            Platform.runLater(() -> pn.getChildren().add(r));
        });
        final Optional<? extends Entity> p = getActiveRoom().getEntities().stream().filter(e -> e instanceof Player).findFirst();
        if (p.isPresent()) {
            final BodyComponent b = p.get().getComponent(BodyComponent.class).get();
            final Rectangle r = new Rectangle();
            r.setX(b.getPosition().getX());
            r.setY(b.getPosition().getY());
            r.setWidth(b.getWidth());
            r.setHeight(b.getHeight());
            r.setStroke(Color.BLUE);
            r.setFill(Color.BLUE);
            Platform.runLater(() -> pn.getChildren().add(r));
        }
    }
}
