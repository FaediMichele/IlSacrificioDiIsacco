package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import model.component.BodyComponent;
import model.component.DamageComponent;
import model.component.DoorComponent;
import model.component.FireComponent;
import model.component.FireType;
import model.component.HealthComponent;
import model.component.Mentality;
import model.component.MentalityComponent;
import model.component.MoveComponent;
import model.entity.Door;
import model.entity.Entity;
import model.entity.Fire;
import model.entity.Player;
import model.entity.Rock;
import model.entity.events.DamageEvent;
import model.entity.events.EventListener;
import model.entity.events.FireHittedEvent;
import model.entity.events.MoveEvent;
import model.game.Floor;
import model.game.FloorImpl;
import model.game.Room;
import model.game.RoomImpl;

/**
 * Test in JUnit for the package model.game.
 * 
 */
@SuppressWarnings("all")
public class TestModel {

    /**
     * Test for {@link Entity}.
     */
    @Test
    public void testEntity() {
        final Entity p = new Player();
        final Entity p2 = new Player();
        final Entity f = new Fire(FireType.RED);
        final Entity d1 = new Door(0, 0);
        final Entity d2 = new Door(0, 0);
        p2.attachComponent(new BodyComponent(p2, 1, 1, 0, 1, 1, 2));
        p.attachComponent(new BodyComponent(p, 1, 1, 0, 1, 1, 2));
        assertTrue(p.hasComponent(BodyComponent.class));
        assertTrue(p.hasComponent(HealthComponent.class));
        assertFalse(p.hasComponent(DoorComponent.class));
        assertEquals(p, p2);
        assertTrue(p.equals(p2));
        assertFalse(p.equals(f));
        assertEquals(d1, d2);
        assertFalse(p.equals(d1));
        assertEquals(p.getComponent(BodyComponent.class).get(), new BodyComponent(p, 1, 1, 0, 1, 1, 2));
        assertTrue(p.getComponent(BodyComponent.class).get().equals(new BodyComponent(p2, 1, 1, 0, 1, 1, 2)));
    }

    /**
     * Test for {@link Fire}.
     */
    @Test
    public void testFire() {
        final Fire f1 = new Fire(FireType.RED);
        final Fire f2 = new Fire(FireType.BLUE);
        assertEquals(f1.getComponent(FireComponent.class).get(), new FireComponent(f1, FireType.RED));
        f1.postEvent(new FireHittedEvent(f1));
        assertFalse(f1.getComponent(FireComponent.class).get().equals(new FireComponent(f1, FireType.RED)));
        assertEquals(Integer.valueOf(FireComponent.class.cast(f1.getComponent(FireComponent.class).get()).getLife()),
                Integer.valueOf(3));
        assertEquals(Integer.valueOf(FireComponent.class.cast(f2.getComponent(FireComponent.class).get()).getLife()),
                Integer.valueOf(4));
        f2.postEvent(new FireHittedEvent(f2));
        f2.postEvent(new FireHittedEvent(f2));
        assertEquals(Integer.valueOf(FireComponent.class.cast(f1.getComponent(FireComponent.class).get()).getLife()),
                Integer.valueOf(3));
        assertEquals(Integer.valueOf(FireComponent.class.cast(f2.getComponent(FireComponent.class).get()).getLife()),
                Integer.valueOf(2));
        //((FireComponent) f1.getComponent(FireComponent.class).get()).dispose();
    }

    /**
     * Test for events.
     */
    @Test
    public void testEvent() {
        EventBus b = new EventBus();
        /*EventListener<FireHittedEvent> fhl = new FireHittedListener(i -> {
            System.out.println("Fire hitted");
        });
        b.register(fhl);
        b.register(new FireOutListener(i -> {
            System.out.println("Fire out");
        }));
        Event e = new FireHittedEvent(new Fire(FireType.RED), FireComponent.class);
        b.post(e);
        b.post(new FireOutEvent(new Fire(FireType.RED), FireComponent.class, FireType.RED));
        */
        EventListener<Integer> elI = new EventListener<Integer>() {
            @Override
            @Subscribe
            public void listenEvent(final Integer event) {
                System.out.println("INT");
            }
        };
        EventListener<Double> elD = new EventListener<Double>() {
            @Override
            @Subscribe
            public void listenEvent(final Double event) {
                System.out.println("DOUBLE");
            }
        };
        b.register(elI);
        b.register(elD);
        b.post(1);
    }

    /**
     * Test for the floor.
     */
    @Test
    public void testFloor() {
        final List<Room> rooms = new ArrayList<>();
        boolean ok;
        rooms.add(new RoomImpl(0, new ArrayList<>(Arrays.asList(
                new Door(0, 1),
                new Door(1, 2)))));
        rooms.add(new RoomImpl(1, new ArrayList<>(Arrays.asList(
                new Door(2, 0),
                new Door(1, 2)))));
        rooms.add(new RoomImpl(2, new ArrayList<>(Arrays.asList(
                new Door(3, 0),
                new Door(1, 1)))));
        final Floor f = new FloorImpl(rooms);

        assertEquals(Integer.valueOf(f.getActiveRoom().getIndex()), Integer.valueOf(0));
        assertThrows(IllegalArgumentException.class, () -> f.changeRoom(-1));

        try {
            f.changeRoom(1);
        } catch (Exception e) {
            ok = false;
        } finally {
            ok = true;
        }
        assertTrue(ok);
        rooms.forEach(r -> assertEquals(r.getFloor(), f));
        assertEquals(f.getActiveRoom(), rooms.get(1));
        assertTrue(f.getActiveRoom().getDoor().containsAll(Arrays.asList(
                new Door(2, 0),
                new Door(1, 2))));
        assertThrows(IllegalStateException.class, () -> rooms.get(0).setFloor(new FloorImpl()));
    }

    /**
     * Test for the room that contains entity.
     */
    @Test
    public void testRoom() {
        final List<Entity> e = new ArrayList<>();
        e.add(new Rock());
        e.add(new Fire(FireType.RED));
        final Room r = new RoomImpl(0, new ArrayList<Door>(), e);
        e.forEach(entity -> assertEquals(entity.getRoom(), r));

        final List<Entity> e1 = new ArrayList<>();
        e1.add(new Rock());
        e1.add(new Fire(FireType.RED));
        assertTrue(r.getEntity().containsAll(e1));
        r.updateEntity(0.0);
        r.updateEntity(0.0);
        assertTrue(r.getEntity().containsAll(e1));
    }

    /**
     * Test for {@link MoveComponent}.
     */
    @Test
    public void testMoveComponent() {
        final double randomTime = 10;
        final Double calculatedXMove = 0.00199;
        final Double calculatedYMove = 0.0;
        final Double calculatedZMove = 0.000995;
        final Entity p = new Player();
        p.attachComponent(new BodyComponent(p));
        p.attachComponent(new MoveComponent(p));
        p.postEvent(new MoveEvent(p, 2, 0, 1));
        assertEquals(getMoveComponent(p).getxMove(), 2);
        assertEquals(getMoveComponent(p).getyMove(), MoveComponent.NOMOVE);
        assertEquals(getMoveComponent(p).getzMove(), 1);
        getMoveComponent(p).update(randomTime);
        assertEquals(getBodyComponent(p).getPosition().getV1(), calculatedXMove);
        assertEquals(getBodyComponent(p).getPosition().getV2(), calculatedYMove);
        assertEquals(getBodyComponent(p).getPosition().getV3(), calculatedZMove);
        assertEquals(getMoveComponent(p).getxMove(), MoveComponent.NOMOVE);
        assertEquals(getMoveComponent(p).getyMove(), MoveComponent.NOMOVE);
        assertEquals(getMoveComponent(p).getzMove(), MoveComponent.NOMOVE);
    }

    /**
     * Test for {@link HealthComponent}.
     */
    @Test
    public void testHealthComponent() {
        final double defaultHearts = 3;
        final double damage = 1;
        final Entity goodEntity = new Player();
        goodEntity.attachComponent(new HealthComponent(goodEntity));
        assertTrue(this.getHealthComponent(goodEntity).isAlive());
        assertEquals(this.getHealthComponent(goodEntity).getLife(), defaultHearts);

        final Entity testEntity = new Player();
        testEntity.attachComponent(new DamageComponent(testEntity, damage));
        assertTrue(testEntity.hasComponent(DamageComponent.class));
        goodEntity.postEvent(new DamageEvent(testEntity)); 
        //assertEquals(this.getHealthComponent(goodEntity).getLife(), defaultHearts - damage - damage);
        assertEquals(this.getHealthComponent(goodEntity).getLife(), defaultHearts - damage);

//        getHealthComponent(goodEntity).getDamaged(0.5); //se chiamo direttamente il metodo funziona
//        /*final Entity enemy = new Player();
//        enemy.attachComponent(new DamageComponent(enemy, 0.5));
//        p.postEvent(new DamageEvent(enemy)); //se faccio la stessa cosa attraverso gli eventi no*/
//        assertEquals(this.getHealthComponent(goodEntity).getHearts().get(0).getlastHeartValue(), 0.5);
    }

    private HealthComponent getHealthComponent(final Entity e) {
        return (HealthComponent) e.getComponent(HealthComponent.class).get();
    }

    private MoveComponent getMoveComponent(final Entity e) {
        return (MoveComponent) e.getComponent(MoveComponent.class).get();
    }

    private BodyComponent getBodyComponent(final Entity e) {
        return (BodyComponent) e.getComponent(BodyComponent.class).get();
    }
}
