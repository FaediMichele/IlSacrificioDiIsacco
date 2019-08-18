package model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.eventbus.EventBus;

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

/**
 * Implementation of Floor.
 * 
 * <p>
 * See also {@link Room}
 *
 */
public class FloorImpl implements Floor {
    //private Pane pn; //ONLY FOR DEBUG
    private static final Pair<Double, Double> ROOMSIZE = new Pair<>(640.0, 344.0);

    private final List<Room> rooms;
    @NotEquals
    @NotHashCode
    private final EventBus eventBus = new EventBus();

    private final GameWorld world;
    private int activeRoomIndex;
    private boolean changedRoom;
    private int maxRoom;

    /**
     * Generate a random floor with random room and random enemy.
     * @param world the world where this floor is.
     */
    public FloorImpl(final GameWorld world) {
        this.rooms = new ArrayList<>();
        this.activeRoomIndex = 0;
        changedRoom = false;
        this.world = world;
    }

    /**
     * Create a floor with specific rooms.
     * 
     * @param rooms rooms to use for this floor.
     * @param world the world where this floor is.
     */
    public FloorImpl(final List<Room> rooms, final GameWorld world) {
        this.rooms = new ArrayList<>(rooms);
        this.rooms.forEach(r -> r.setFloor(this));
        changedRoom = false;
        this.world = world;
    }

    /**
     * Create the {@link Floor} via xml.
     * 
     * @param floorName the floor name in xml.
     * @param world the world where this floor is.
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public FloorImpl(final String floorName, final GameWorld world)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        this.world = world;
        final Document docXML = StaticMethodsUtils.getDocumentXML("/xml/Floor.xml");
        final List<Node> ls = StaticMethodsUtils
                .getNodesFromNodelList(docXML.getElementsByTagName(floorName).item(0).getChildNodes());
        maxRoom = Integer.parseInt(
                docXML.getElementsByTagName(floorName).item(0).getAttributes().getNamedItem("MaxRoom").getNodeValue());
        this.rooms = new ArrayList<>();
        generateRooms();
        Optional<Node> node = ls.stream().filter(n -> n.getNodeName().equals("Rooms")).findFirst();
        if (!node.isPresent()) {
            throw new IllegalStateException("tag Rooms not found");
        }
        this.activeRoomIndex = 0;
        node = ls.stream().filter(n -> n.getNodeName().equals("Boss")).findFirst();
        if (!node.isPresent()) {
            throw new IllegalStateException("tag Boss not found");
        }
        final Random rnd = new Random();
        final Pair<Integer, Integer> specialRooms = addGenericRooms(node.get(), rnd);
        addSpecialRoom(node.get(), rnd, specialRooms.getX());
        node = ls.stream().filter(n -> n.getNodeName().equals("Treasure")).findFirst();
        if (!node.isPresent()) {
            throw new IllegalStateException("tag Treasure not found");
        }
        addSpecialRoom(node.get(), rnd, specialRooms.getY());
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
    public List<Room> getRooms() {
        return this.rooms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameWorld getGameWorld() {
        return world;
    }

    private void addSpecialRoom(final Node node, final Random rnd, final int index) {
        final NodeList nl = node.getChildNodes();
        final int n = nl.getLength();
        final int rndIndex = rnd.nextInt((n - 1) / 2);
        final Node room = nl.item(rndIndex * 2 + 1);
        if (room.getNodeType() == Node.ELEMENT_NODE) {
            final String roomName = room.getNodeName();
            rooms.get(index).fill(roomName);
        }
    }

    /**
     * Create all generic rooms.
     * @param node the node to get the information.
     * @param rnd a random initialized.
     * @return the index for the boss and treasure rooms.
     */
    private Pair<Integer, Integer> addGenericRooms(final Node node, final Random rnd) {
        final NodeList nl = node.getChildNodes();
        final Integer b = rnd.nextInt(rooms.size() - 1) + 1;
        Integer t;
        do {
            t = rnd.nextInt(rooms.size() - 1) + 1;
        } while (t.equals(b));
        for (int i = 1; i < rooms.size() - 2; i++) {
            if (!b.equals(i) && !t.equals(i)) {
                final int n = nl.getLength();
                final int rndIndex = rnd.nextInt((n - 1) / 2);
                final Node room = nl.item(rndIndex * 2 + 1);
                if (room.getNodeType() == Node.ELEMENT_NODE) {
                    final String roomName = room.getNodeName();
                    rooms.get(i).fill(roomName);
                }
            }
        }
        return new Pair<>(b, t);
    }

    private void generateRooms() {
        final Random rnd = new Random();
        final int nRooms = rnd.nextInt(maxRoom / 2) + maxRoom / 2;
        final Matrix<Integer> map = new Matrix<>(maxRoom, maxRoom);
        final List<Pair<Integer, Integer>> roomsPosition = populateMap(map, rnd, nRooms);
        for (int i = 0; i < roomsPosition.size(); i++) {
            rooms.add(generateEmptyRoom(map, roomsPosition.get(i), i));
        }
    }

    private List<Pair<Integer, Integer>> populateMap(final Matrix<Integer> m, final Random rnd, final int nRooms) {
        final Pair<Integer, Integer> position = new Pair<>(maxRoom / 2, maxRoom / 2);
        final List<Pair<Integer, Integer>> ret = new ArrayList<>();
        int n = 0;
        int directionCounted;
        m.set(position.getX(), position.getY(), n);
        ret.add(new Pair<>(position.getX(), position.getY()));
        n++;
        int direction;
        while (n < nRooms) {
            direction = rnd.nextInt(4); // 0, 1, 2, 3
            directionCounted = 0;
            while (!canGoDirection(m, position, direction) && directionCounted < 3) {
                direction = (direction + 1) % 4;
                directionCounted++;
            }
            if (directionCounted >= 3) {
                break;
            }
            updatePosition(position, direction);
            m.set(position.getX(), position.getY(), n);
            ret.add(new Pair<>(position.getX(), position.getY()));
            n++;
        }
        return ret;
    }

    private Room generateEmptyRoom(final Matrix<Integer> map, final Pair<Integer, Integer> position, final int index) {
        final List<Door> doors = new ArrayList<>();
        if (!canGoDirection(map, position, 0)) {
            doors.add(new Door(BasicMovementEnum.UP, index, map.get(position.getX(), position.getY() + 1), ROOMSIZE));
        }
        if (!canGoDirection(map, position, 1)) {
            doors.add(new Door(BasicMovementEnum.RIGHT, index, map.get(position.getX() + 1, position.getY()), ROOMSIZE));
        }
        if (!canGoDirection(map, position, 2)) {
            doors.add(new Door(BasicMovementEnum.DOWN, index, map.get(position.getX(), position.getY() - 1), ROOMSIZE));
        }
        if (!canGoDirection(map, position, 3)) {
            doors.add(new Door(BasicMovementEnum.LEFT, index, map.get(position.getX() - 1, position.getY()), ROOMSIZE));
        }
        if (doors.isEmpty()) {
            throw new IllegalStateException();
        }
        final Room ret = new RoomImpl(index, ROOMSIZE.getX(), ROOMSIZE.getY());
        doors.forEach(d -> ret.insertEntity(d));
        ret.insertEntity(new Wall(BasicMovementEnum.UP, ROOMSIZE));
        ret.insertEntity(new Wall(BasicMovementEnum.RIGHT, ROOMSIZE));
        ret.insertEntity(new Wall(BasicMovementEnum.DOWN, ROOMSIZE));
        ret.insertEntity(new Wall(BasicMovementEnum.LEFT, ROOMSIZE));
        ret.setFloor(this);
        ret.updateEntityList();
        return ret;
    }

    private boolean canGoDirection(final Matrix<Integer> m, final Pair<Integer, Integer> position, final int direction) {
        if (direction == 0) { // NORD.
            return position.getY() > -1
                    && position.getY() + 1 < m.getHeight() - 1
                    && m.get(position.getX(), position.getY() + 1) == null;
        } else if (direction == 1) { // EAST.
            return position.getX() + 1 > 0
                    && position.getX() + 1 < m.getWidth() - 1
                    && m.get(position.getX() + 1, position.getY()) == null;
        } else if (direction == 2) { // SOUTH.
            return position.getY() > 2
                    && position.getY() - 1 < m.getHeight() 
                    && m.get(position.getX(), position.getY() - 1) == null;
        } else if (direction == 3) { // WEST.
            return position.getX() > 2
                    && position.getX() - 1 < m.getWidth()
                    && m.get(position.getX() - 1, position.getY()) == null;
        } else {
            throw new IllegalStateException();
        }
    }

    private void updatePosition(final Pair<Integer, Integer> position, final int direction) {
        if (direction == 0) { // NORD.
            position.setY(position.getY() + 1);
        }
        if (direction == 1) { // EAST.
            position.setX(position.getX() + 1);
        }
        if (direction == 2) { // SOUTH.
            position.setY(position.getY() - 1);
        }
        if (direction == 3) { // WEST.
            position.setX(position.getX() - 1);
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
        //debug();
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
    /*private void debug() {
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
        getActiveRoom().getEntities().forEach(e -> {
            final BodyComponent b = e.getComponent(BodyComponent.class).get();
            final Rectangle r = new Rectangle();
            r.setX(b.getPosition().getX());
            r.setY(b.getPosition().getY());
            r.setWidth(b.getWidth());
            r.setHeight(b.getHeight());
            r.setStroke(Color.WHITE);
            r.setFill(Color.WHITE);
            Platform.runLater(() -> pn.getChildren().add(r));
        });
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
    }*/
}
