package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import model.component.AbstractPickupableComponent;
import model.component.BlackHeart;
import model.component.BodyComponent;
import model.component.BombCollectibleComponent;
import model.component.CollisionComponent;
import model.component.DamageComponent;
import model.component.DoorComponent;
import model.component.FireComponent;
import model.component.FireType;
import model.component.HealthComponent;
import model.component.InventoryComponent;
import model.component.Mentality;
import model.component.MentalityComponent;
import model.component.MoveComponent;
import model.component.SimpleHeart;
import model.entity.Bomb;
import model.entity.Door;
import model.entity.Entity;
import model.entity.Fire;
import model.entity.Player;
import model.entity.Rock;
import model.events.CollisionEvent;
import model.events.DamageEvent;
import model.events.FireHittedEvent;
import model.events.MoveEvent;
import model.events.ReleaseEvent;
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
        // ((FireComponent) f1.getComponent(FireComponent.class).get()).dispose();
    }

    /**
     * Test for the floor.
     */
    @Test
    public void testFloor() {
        final List<Room> rooms = new ArrayList<>();
        boolean ok;
        rooms.add(new RoomImpl(0, new ArrayList<>(Arrays.asList(new Door(0, 1), new Door(1, 2)))));
        rooms.add(new RoomImpl(1, new ArrayList<>(Arrays.asList(new Door(2, 0), new Door(1, 2)))));
        rooms.add(new RoomImpl(2, new ArrayList<>(Arrays.asList(new Door(3, 0), new Door(1, 1)))));
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
        assertTrue(f.getActiveRoom().getDoor().containsAll(Arrays.asList(new Door(2, 0), new Door(1, 2))));
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
        final Entity goodEntity = new Player();
        goodEntity.attachComponent(new HealthComponent(goodEntity));
        assertTrue(this.getHealthComponent(goodEntity).isAlive());
        assertEquals(this.getHealthComponent(goodEntity).getLife(), defaultHearts);

        final Entity firstEnemy = new Player();
        final double firstDamage = 0.4;
        firstEnemy.attachComponent(new DamageComponent(firstEnemy, firstDamage));
        goodEntity.postEvent(new DamageEvent(firstEnemy));
        assertEquals(this.getHealthComponent(goodEntity).getLife(), defaultHearts - firstDamage);
        assertEquals(this.getHealthComponent(goodEntity).getNumberOfHearts(), 3);

        final Entity secondEnemy = new Player();
        final double secondDamage = 1.5;
        secondEnemy.attachComponent(new DamageComponent(secondEnemy, secondDamage));
        goodEntity.postEvent(new DamageEvent(secondEnemy));
        assertEquals(this.getHealthComponent(goodEntity).getNumberOfHearts(), 2);
        assertTrue(this.getHealthComponent(goodEntity).isAlive());
        assertEquals(this.getHealthComponent(goodEntity).getLife(), defaultHearts - firstDamage - secondDamage);

        final Entity thirdEnemy = new Player();
        final double thirdDamage = 0.1;
        thirdEnemy.attachComponent(new DamageComponent(thirdEnemy, thirdDamage));
        goodEntity.postEvent(new DamageEvent(thirdEnemy));
        assertTrue(this.getHealthComponent(goodEntity).isAlive());
        assertEquals(this.getHealthComponent(goodEntity).getLife(), 1);
        assertEquals(this.getHealthComponent(goodEntity).getNumberOfHearts(), 1);

        final Entity fourthEnemy = new Player();
        final double fourthDamage = 1.0;
        fourthEnemy.attachComponent(new DamageComponent(fourthEnemy, fourthDamage));
        goodEntity.postEvent(new DamageEvent(fourthEnemy));
        assertFalse(this.getHealthComponent(goodEntity).isAlive());
        assertEquals(this.getHealthComponent(goodEntity).getLife(), 0);
    }

    /**
     * Test for {@link BlackHeart}.
     */
    @Test
    public void testBlackHeart() {
        final int defaultHearts = 3;
        final int newHearts = 2;
        final Entity goodEntity = new Player();
        goodEntity.attachComponent(new HealthComponent(goodEntity, defaultHearts));
        this.getHealthComponent(goodEntity).addHeart(new SimpleHeart());
        this.getHealthComponent(goodEntity).addHeart(new BlackHeart.Builder(goodEntity).build());
        assertTrue(this.getHealthComponent(goodEntity).isAlive());
        assertEquals(this.getHealthComponent(goodEntity).getLife(), defaultHearts + newHearts);

        final Entity enemy = new Player();
        final double damage = 0.7;
        final double finalEnemyLife = 2.7;
        enemy.attachComponent(new DamageComponent(enemy, damage));
        enemy.attachComponent(new HealthComponent(enemy));
        enemy.attachComponent(new MentalityComponent(enemy, Mentality.EVIL));
        goodEntity.postEvent(new DamageEvent(enemy));
        assertEquals(this.getHealthComponent(enemy).getNumberOfHearts(), 3);
        assertEquals(this.getHealthComponent(enemy).getLife(), 3);

        final List<Entity> entities = new ArrayList<>();
        entities.add(goodEntity);
        entities.add(enemy);
        new RoomImpl(0, new LinkedList<Door>(), entities);
        goodEntity.postEvent(new DamageEvent(enemy));
        assertEquals(this.getHealthComponent(enemy).getNumberOfHearts(), 3);
        assertEquals(this.getHealthComponent(enemy).getLife(), finalEnemyLife);
        assertEquals(this.getHealthComponent(goodEntity).getNumberOfHearts(), defaultHearts + newHearts - 1);
    }

    /**
     * Test for {@link CollisionComponent}.
     */
    @Test
    public void testCollisionComponent() {
        final Player playerA = new Player();
        final Player playerB = new Player();
        final double damage = 0.2;
        final double settingBombTest = 0.5;
        final int numberOfThings = 1;
        double life;

        playerB.attachComponent(new MentalityComponent(playerB, Mentality.EVIL))
                .attachComponent(new DamageComponent(playerB, damage));
        life = ((HealthComponent) playerA.getComponent(HealthComponent.class).get()).getLife();
        playerA.postEvent(new CollisionEvent(playerB));
        assertEquals(life - damage, ((HealthComponent) playerA.getComponent(HealthComponent.class).get()).getLife());

        playerB.attachComponent(new MentalityComponent(playerB, Mentality.GOOD));
        playerA.postEvent(new CollisionEvent(playerB));
        assertEquals(life - damage, ((HealthComponent) playerA.getComponent(HealthComponent.class).get()).getLife());

        playerB.attachComponent(
                new BombCollectibleComponent(playerB, settingBombTest, (int) settingBombTest, (int) settingBombTest));
        Room room = new RoomImpl(2, null);
        room.insertEntity(playerB);
        playerA.postEvent(new CollisionEvent(playerB));
        assertEquals(true, playerB.hasComponent(AbstractPickupableComponent.class));
        assertEquals(numberOfThings, ((InventoryComponent) playerA.getComponent(InventoryComponent.class).get()).getThings().size());
    }

    /**
     * Test for {@link InventoryComponent}.
     */
    @Test
    public void testInventoryComponent() {
        final Player p = new Player();
        final Bomb b = new Bomb();
        final Bomb b2 = new Bomb();

        Room room = new RoomImpl(2, null);
        room.insertEntity(p);
        room.insertEntity(b);
        assertEquals(room.getEntity().size(), 2);
        p.postEvent(new CollisionEvent(b));
        assertEquals(1, ((InventoryComponent) p.getComponent(InventoryComponent.class).get()).getThings().size());
        assertTrue(((InventoryComponent) p.getComponent(InventoryComponent.class).get()).getThings().contains(b));

        room.insertEntity(b2);
        p.postEvent(new CollisionEvent(b2));
        assertEquals(true, b2.hasComponent(AbstractPickupableComponent.class));
        assertEquals(2, ((InventoryComponent) p.getComponent(InventoryComponent.class).get()).getThings().size());
        assertTrue(((InventoryComponent) p.getComponent(InventoryComponent.class).get()).getThings().contains(b2));

        p.postEvent(new ReleaseEvent(p, b.getClass()));
        assertEquals(1, ((InventoryComponent) p.getComponent(InventoryComponent.class).get()).getThings().size());
        assertTrue(((InventoryComponent) p.getComponent(InventoryComponent.class).get()).getThings().contains(b2));
        assertEquals(room.getEntity().size(), 2);
        assertTrue(room.getEntity().contains(b));
        assertEquals(getBodyComponent(p).getPosition(), getBodyComponent(b).getPosition());
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
