package model.game;

import java.util.ArrayList;
import java.util.LinkedHashSet;
//import java.util.Random;
import java.util.Set;

//import model.component.DoorComponent;

/**
 * Implementation of Floor.
 * 
 * <p>See also {@link Room}
 *
 */
public class FloorImpl implements Floor {

    //private static final int MAXROOM = 6;

    private final ArrayList<? extends Room> rooms = new ArrayList<>();
    private int activeRoomIndex = 0;

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
    public Set<Room> getRooms() {
        return new LinkedHashSet<>(rooms);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateRooms() {
        // TODO

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
