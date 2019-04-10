package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;


import model.component.BodyComponent;
import model.component.DoorComponent;
import model.component.HealthComponent;
import model.entity.Door;
import model.entity.Entity;
import model.entity.Fire;
import model.entity.Player;
import model.game.Floor;
import model.game.FloorImpl;
import model.game.Room;
import model.game.RoomImpl;
/**
 * Test in JUnit for the package model.game.
 *
 */
public class TestModel {

    /**
     * Test for {@link Entity}.
     */
    @org.junit.Test
    public void testEntity() {
        Entity p = new Player();
        Entity p2 = new Player();
        Entity f = new Fire();
        p2.attachComponent(new BodyComponent(1, 1, 0, 1, 1, 2));
        p.attachComponent(new BodyComponent(1, 1, 0, 1, 1, 2));
        assertEquals(p.hasComponent(BodyComponent.class), true);
        assertEquals(p.hasComponent(HealthComponent.class), true);
        assertEquals(p.hasComponent(DoorComponent.class), false);
        assertEquals(p, p2);
        assertFalse(p.equals(f));
        System.out.println(BodyComponent.class.cast(p.getComponent(BodyComponent.class).get()).getX());
        //assertEquals(p.getComponent(BodyComponent.class).get(), Optional.of(new BodyComponent(1, 1, 0, 1, 1, 2)));
    }

    /**
     * Test for {@link Fire}.
     */
    @org.junit.Test
    public void testEntity() {
        Fire f = new Fire(new BodyComponent(e));
    }
    /**
     * Test for the map.
     */
    @org.junit.Test
    public void testMap() {
        boolean ok = false;
        ArrayList<Door> doors = new ArrayList<Door>();
        doors.add(new Door(0, new DoorComponent(0, 1)));
        doors.add(new Door(0, new DoorComponent(0, 2)));
        Room r1 = new RoomImpl(0, doors);
        ArrayList<Door> doorsTmp = new ArrayList<Door>();
        doorsTmp.add(new Door(0, new DoorComponent(0, 1)));
        doorsTmp.add(new Door(0, new DoorComponent(0, 2)));
        LinkedHashSet<Door> tmp = new LinkedHashSet<Door>(doorsTmp);
        assertEquals(r1.getDoor(), tmp);

        doors = new ArrayList<Door>();
        doors.add(new Door(2, new DoorComponent(1, 0)));
        Room r2 = new RoomImpl(1, doors);

        doors = new ArrayList<Door>();
        doors.add(new Door(3, new DoorComponent(2, 0)));
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
                new Door(2, new DoorComponent(1, 0)))));

        try {
            floor.changeRoom(2);
        } catch (Exception e) {
            ok = false;
        } finally {
            ok = true;
        }
        assertTrue(ok);

        assertEquals(floor.getActiveRoom().getDoor(), new LinkedHashSet<Door>(Arrays.asList(
                new Door(3, new DoorComponent(2, 0)))));
    }
}
