package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import model.component.BodyComponent;
import model.component.DoorComponent;
import model.component.FireComponent;
import model.component.HealthComponent;
import model.entity.Door;
import model.entity.Entity;
import model.entity.Fire;
import model.entity.Player;
import model.entity.events.FireHittedEvent;
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
        final Entity p = new Player();
        final Entity p2 = new Player();
        final Entity f = new Fire();
        p2.attachComponent(new BodyComponent(p2, 1.0, 1, 0, 1, 1, 2));
        p.attachComponent(new BodyComponent(p, 1, 1, 0, 1, 1, 2));
        assertTrue(p.hasComponent(BodyComponent.class));
        assertTrue(p.hasComponent(HealthComponent.class));
        assertFalse(p.hasComponent(DoorComponent.class));
        assertEquals(p, p2);
        assertFalse(p.equals(f));
        System.out.println(BodyComponent.class.cast(p.getComponent(BodyComponent.class).get()).getX());
        //assertEquals(p.getComponent(BodyComponent.class).get(), Optional.of(new BodyComponent(1, 1, 0, 1, 1, 2)));
    }

    /**
     * Test for {@link Fire}.
     */
    @org.junit.Test
    public void testFire() {
        final Fire f1 = new Fire();
        final Fire f2 = new Fire();
        assertEquals(f1.getComponent(FireComponent.class).get(), new FireComponent(f1));
        f1.postEvent(new FireHittedEvent(f1, FireComponent.class));
        assertFalse(f1.getComponent(FireComponent.class).get().equals(new FireComponent(f1)));
        assertEquals(Integer.valueOf(FireComponent.class.cast(f1.getComponent(FireComponent.class).get()).getLife()), Integer.valueOf(3));
        assertEquals(Integer.valueOf(FireComponent.class.cast(f2.getComponent(FireComponent.class).get()).getLife()), Integer.valueOf(4));
        f2.postEvent(new FireHittedEvent(f2, FireComponent.class));
        f2.postEvent(new FireHittedEvent(f2, FireComponent.class));
        assertEquals(Integer.valueOf(FireComponent.class.cast(f1.getComponent(FireComponent.class).get()).getLife()), Integer.valueOf(3));
        assertEquals(Integer.valueOf(FireComponent.class.cast(f2.getComponent(FireComponent.class).get()).getLife()), Integer.valueOf(2));
    }
    /**
     * Test for the map.
     */
    @org.junit.Test
    public void testMap() {
        boolean ok = false;
        ArrayList<Door> doors = new ArrayList<Door>();
        doors.add(new Door(0, 1));
        doors.add(new Door(0, 2));
        final Room r1 = new RoomImpl(0, doors);
        final List<Door> doorsTmp = new ArrayList<Door>();
        doorsTmp.add(new Door(0, 1));
        doorsTmp.add(new Door(0, 2));
        final LinkedHashSet<Door> tmp = new LinkedHashSet<Door>(doorsTmp);
        assertEquals(r1.getDoor(), tmp);

        doors = new ArrayList<Door>();
        doors.add(new Door(1, 0));
        final Room r2 = new RoomImpl(1, doors);

        doors = new ArrayList<Door>();
        doors.add(new Door(2, 0));
        final Room r3 = new RoomImpl(2, doors);

        final Floor floor = new FloorImpl(new ArrayList<Room>(Arrays.asList(r1, r2, r3)));

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
                new Door(1, 0))));

        try {
            floor.changeRoom(2);
        } catch (Exception e) {
            ok = false;
        } finally {
            ok = true;
        }
        assertTrue(ok);

        assertEquals(floor.getActiveRoom().getDoor(), new LinkedHashSet<Door>(Arrays.asList(
                new Door(2, 0))));
    }
}
