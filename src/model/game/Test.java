package model.game;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;


import model.component.DoorComponent;
import model.entity.Door;
/**
 * Test in JUnit for the package model.game.
 *
 */
public class Test {

    /**
     * Test for the map.
     */
    @org.junit.Test
    public void testMap() {
        boolean ok = false;
        ArrayList<Door> doors = new ArrayList<Door>();
        doors.add(new Door(0, new DoorComponent(1)));
        doors.add(new Door(1, new DoorComponent(2)));
        Room r1 = new RoomImpl(0, doors);
        System.out.println(r1.getDoor());
        assertEquals(r1.getDoor(), new LinkedHashSet<Door>(Arrays.asList(
                new Door(0, new DoorComponent(1)),
                new Door(1, new DoorComponent(2)))));

        doors = new ArrayList<Door>();
        doors.add(new Door(2, new DoorComponent(0)));
        Room r2 = new RoomImpl(1, doors);

        doors = new ArrayList<Door>();
        doors.add(new Door(3, new DoorComponent(0)));
        Room r3 = new RoomImpl(2, doors);

        Floor floor = new FloorImpl(new ArrayList<Room>(Arrays.asList(r1, r2, r3)));

        assertThrows(IllegalArgumentException.class, () -> floor.changeRoom(-1));

        try {
            floor.changeRoom(1);
        } catch (Exception e) {
            ok = false;
        } finally {
            ok = true;
        }
        assertTrue(ok);

        assertEquals(floor.getActiveRoom().getDoor(), new LinkedHashSet<Door>(Arrays.asList(
                new Door(2, new DoorComponent(0)))));

        try {
            floor.changeRoom(2);
        } catch (Exception e) {
            ok = false;
        } finally {
            ok = true;
        }
        assertTrue(ok);

        assertEquals(floor.getActiveRoom().getDoor(), new LinkedHashSet<Door>(Arrays.asList(
                new Door(3, new DoorComponent(0)))));
    }
}
