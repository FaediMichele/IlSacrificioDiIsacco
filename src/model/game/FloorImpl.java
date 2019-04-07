package model.game;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import model.component.DoorComponent;
import model.entity.Door;
import util.Matrix;
import util.Pair;

/**
 * Implementation of Floor.
 * 
 * <p>See also {@link Room}
 *
 */
public class FloorImpl implements Floor {

    private static final int MAXROOM = 6;
    private static final int NORD = 0;
    private static final int EAST = 1;
    private static final int SUD = 2;
    private static final int OVEST = 3;

    private final ArrayList<Room> rooms;
    private int activeRoomIndex = 0;

    /**
     * Generate a random floor with random room and random enemy.
     */
    public FloorImpl() {
        rooms = new ArrayList<>();
        generateRooms();
    }

    /**
     * Create a floor with specific rooms.
     * @param rooms rooms to use for this floor
     */
    public FloorImpl(final ArrayList<Room> rooms) {
        this.rooms = new ArrayList<>(rooms);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Room getActiveRoom() {
        return rooms.get(activeRoomIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeRoom(final Integer index) {
        if (rooms.stream().filter(r -> r.getIndex() == index).count() != 1) {
            throw new IllegalArgumentException("Room not found");
        }
        activeRoomIndex = index;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Room> getRooms() {
        return new LinkedHashSet<>(rooms);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateRooms() {
        if (rooms.size() != 0) {
            throw new IllegalStateException("Floor already created");
        }
        Matrix<Integer> m = new Matrix<>(MAXROOM, MAXROOM);
        ArrayList<Pair<Integer, Integer>> roomIndexs = new ArrayList<>();
        generateMap(m, roomIndexs);

        for (int index = 0; index < roomIndexs.size(); index++) {
            rooms.add(createEmptyRoom(index, m, roomIndexs.get(index)));
        }

    }

    /**
     * Generate an empty room based on index and adjacent room. 
     * @param index the index of the room
     * @param m Matrix of adjacent room
     * @param pos the position on the matrix of the room to initialize
     * @return the room
     */
    private Room createEmptyRoom(final int index, final Matrix<Integer> m, final Pair<Integer, Integer> pos) {
        ArrayList<Door> doors = new ArrayList<>();
        if (roomExist(m, pos.getX(), pos.getY() - 1)) { //NORD
            doors.add(new Door(NORD, new DoorComponent(m.get(pos.getX(), pos.getY() - 1))));
        }
        if (roomExist(m, pos.getX() + 1, pos.getY())) { //EAST
            doors.add(new Door(EAST, new DoorComponent(m.get(pos.getX() + 1, pos.getY()))));
        }
        if (roomExist(m, pos.getX(), pos.getY() + 1)) { //SUD
            doors.add(new Door(SUD, new DoorComponent(m.get(pos.getX(), pos.getY() + 1))));
        }
        if (roomExist(m, pos.getX() - 1, pos.getY())) { //OVEST
            doors.add(new Door(OVEST, new DoorComponent(m.get(pos.getX() - 1, pos.getY()))));
        }
        return new RoomImpl(index, doors);
    }

    /**
     * Generate the map.
     * @param m {@link Matrix} where to save the room ( used for nearby rooms)
     * @param roomIndexs {@link ArrayList} of positions of each room in increasing order
     */
    private void generateMap(final Matrix<Integer> m,  final ArrayList<Pair<Integer, Integer>> roomIndexs) {
        Pair<Integer, Integer> pos = new Pair<>(MAXROOM / 2, MAXROOM / 2);
        Random rnd = new Random();
        int nRoom = rnd.nextInt(MAXROOM);

        for (int index = 0; index < nRoom; index++) {
            int direction = rnd.nextInt(OVEST);
            int directionCounted = 0;
            while (!canGoDirection(m, pos, direction) && directionCounted < OVEST) {
                direction = (direction + 1) % OVEST;
                directionCounted++;
            }
            if (directionCounted == OVEST) { // No other direction is possible
                index = nRoom;
            } else {
                updatePosition(pos, direction);
                m.set(pos.getX(), pos.getY(), index + 1);
                roomIndexs.add(new Pair<Integer, Integer>(pos.getX(), pos.getY()));
            }
        }
    }

    /**
     * Says if the next room can be in the direction.
     * @param m the matrix
     * @param posX the actual X
     * @param posY the actual Y
     * @param direction the direction to verify (0= Nord; 1= East; 2= Sud; 3= Ovest)
     * @return if the next room can be in the direction
     */
    private boolean canGoDirection(final Matrix<?> m, final Pair<Integer, Integer> pos, final int direction) {
        switch (direction) {
        case NORD:
            return pos.getY() > 0 && m.get(pos.getX(),  pos.getY() - 1) == null;
        case EAST:
            return pos.getX() < m.getWidth() - 1 && m.get(pos.getX() + 1, pos.getY()) == null;
        case SUD:
            return pos.getY() < m.getWidth() - 1 && m.get(pos.getX(),  pos.getY() + 1) == null;
        case OVEST:
            return pos.getX() > 0 && m.get(pos.getX() - 1,  pos.getY()) == null;
        default:
            throw new IllegalArgumentException();
        }
    }

    private boolean roomExist(final Matrix<Integer> m, final int posX, final int posY) {
        return posX >= 0 && posX < m.getWidth() && posY >= 0 && posY < m.getHeight()
                && m.get(posX, posY) != null;
    }

    /**
     * Update the position based on the direction.
     * @param pos the current position
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
        return;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Double deltaTime) {
        int nextRoom = -1;
        rooms.get(activeRoomIndex).updateEntity(deltaTime);
        /*rooms.get(activeRoomIndex).getDoor().stream()
                .filter(e -> ((DoorComponent) e.getComponent(DoorComponent.class).get()).playerPassed())
                .forEach(e -> nextRoom = ((DoorComponent) e.getComponent(DoorComponent.class).get()).getDestination());
                */
        if (nextRoom != -1) {
            activeRoomIndex = nextRoom;
        }
    }
}
