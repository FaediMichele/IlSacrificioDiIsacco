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

import model.component.BlackHeart;
import model.component.BodyComponent;
import model.component.DamageComponent;
import model.component.DoorAIComponent;
import model.component.FireAIComponent;
import model.component.FireType;
import model.component.HealthComponent;
import model.component.InventoryComponent;
import model.component.MoveComponent;
import model.component.SimpleHeart;
import model.component.StatusComponent;
import model.component.collectible.AbstractPickupableComponent;
import model.component.collectible.BombCollectableComponent;
import model.component.mentality.AbstractMentalityComponent;
import model.component.mentality.EnemyMentalityComponent;
import model.component.mentality.PlayerMentalityComponent;
import model.entity.Bomb;
import model.entity.Door;
import model.entity.Entity;
import model.entity.Fire;
import model.entity.Heart;
import model.entity.Player;
import model.entity.Rock;
import model.entity.SimpleEnemyMovable;
import model.entity.Tear;
import model.events.CollisionEvent;
import model.events.DamageEvent;
import model.events.FireHittedEvent;
import model.events.MoveEvent;
import model.events.TearShotEvent;
import model.events.UseThingEvent;
import model.game.Floor;
import model.game.FloorImpl;
import model.game.Room;
import model.game.RoomImpl;
import util.Pair;
import util.Triplet;

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
        assertFalse(p.hasComponent(DoorAIComponent.class));
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
        assertEquals(f1.getComponent(FireAIComponent.class).get(), new FireAIComponent(f1, FireType.RED));
        f1.postEvent(new FireHittedEvent(f1));
        assertFalse(f1.getComponent(FireAIComponent.class).get().equals(new FireAIComponent(f1, FireType.RED)));
        assertEquals(
                Integer.valueOf(FireAIComponent.class.cast(f1.getComponent(FireAIComponent.class).get()).getLife()),
                Integer.valueOf(3));
        assertEquals(
                Integer.valueOf(FireAIComponent.class.cast(f2.getComponent(FireAIComponent.class).get()).getLife()),
                Integer.valueOf(4));
        f2.postEvent(new FireHittedEvent(f2));
        f2.postEvent(new FireHittedEvent(f2));
        assertEquals(
                Integer.valueOf(FireAIComponent.class.cast(f1.getComponent(FireAIComponent.class).get()).getLife()),
                Integer.valueOf(3));
        assertEquals(
                Integer.valueOf(FireAIComponent.class.cast(f2.getComponent(FireAIComponent.class).get()).getLife()),
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
        final Entity p = new Player();
        p.attachComponent(new BodyComponent(p));
        p.attachComponent(new MoveComponent(p));
        p.postEvent(new MoveEvent(p, 2, 0, -1));
        assertEquals(getMoveComponent(p).getxMove(), 2);
        assertEquals(getMoveComponent(p).getyMove(), MoveComponent.NOMOVE);
        assertEquals(getMoveComponent(p).getzMove(), -1);
        getMoveComponent(p).update(randomTime);
        assertTrue(getBodyComponent(p).getPosition().getV1() > 0.0);
        assertEquals(getBodyComponent(p).getPosition().getV2(), 0.0);
        assertTrue(getBodyComponent(p).getPosition().getV3() < 0.0);
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
        enemy.attachComponent(new EnemyMentalityComponent(enemy));
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
        final Entity player = new Player();
        final Entity good = new Player();
        final Entity enemy = new SimpleEnemyMovable();
        final Entity collectible = new Player();
        collectible.attachComponent(new BombCollectableComponent(collectible, 1, 1, 1));
        final double damage = 0.2;
        final double settingBombTest = 0.5;
        final int numberOfThings = 1;
        double life = getHealthComponent(player).getLife();

        enemy.attachComponent(new DamageComponent(enemy, damage));
        player.postEvent(new CollisionEvent(enemy));
        assertEquals(life - damage, getHealthComponent(player).getLife());

        good.attachComponent(new DamageComponent(good, damage));
        player.postEvent(new CollisionEvent(good));
        assertEquals(life - damage, getHealthComponent(player).getLife());

        Room room = new RoomImpl(2, null);
        room.insertEntity(collectible);
        assertTrue(player.hasComponent(StatusComponent.class));
        assertTrue(collectible.hasComponent(StatusComponent.class));
        player.postEvent(new CollisionEvent(collectible));

        assertTrue(collectible.hasComponent(AbstractPickupableComponent.class));
        assertEquals(numberOfThings, getInventoryComponent(player).getThings().size());
        assertEquals(0, room.getEntity().size());
    }

    /**
     * Test for {@link StatusComponent}.
     */
    @Test
    public void testStatusComponent() {
        Entity entity = new Player();
        entity.getStatusComponent().setStatus(new Pair<>(1, "pick up"));
        assertEquals("pick up",   entity.getStatusComponent().getStatus().get(0));
    }

    /**
     * Test for {@link InventoryComponent}.
     */
    @Test
    public void testInventoryComponent() {
        final Player player = new Player();
        final Bomb bomb = new Bomb();

        Room room = new RoomImpl(2, null);
        room.insertEntity(player);
        room.insertEntity(bomb);
        assertEquals(room.getEntity().size(), 2);

        player.postEvent(new CollisionEvent(bomb));
        assertEquals(1, getInventoryComponent(player).getThings().size());
        assertTrue(getInventoryComponent(player).getThings().contains(bomb));

        final Bomb bomb2 = new Bomb();
        room.insertEntity(bomb2);
        player.postEvent(new CollisionEvent(bomb2));
        assertEquals(true, bomb2.hasComponent(AbstractPickupableComponent.class));
        assertEquals(2, getInventoryComponent(player).getThings().size());
        assertTrue(getInventoryComponent(player).getThings().contains(bomb2));

        player.postEvent(new UseThingEvent(player, bomb.getClass()));
        assertEquals(1, getInventoryComponent(player).getThings().size());
        assertTrue(getInventoryComponent(player).getThings().contains(bomb2));
        assertEquals(room.getEntity().size(), 2);
        assertTrue(room.getEntity().contains(bomb));
        assertEquals(getBodyComponent(player).getPosition(), getBodyComponent(bomb).getPosition());

        final Heart heart = new Heart();
        room.insertEntity(heart);
        assertEquals(getHealthComponent(player).getHearts().size(), 3);
        player.postEvent(new CollisionEvent(heart));
        assertEquals(getHealthComponent(player).getHearts().size(), 4);

        // TO-DO: pick up and use also a key
    }

    /**
     * Test for {@link InventoryComponent}.
     */
    @Test
    public void testMentalityComponent() {
        Player p = new Player();
        AbstractMentalityComponent m = (AbstractMentalityComponent) p.getComponent(AbstractMentalityComponent.class)
                .get();
        assertEquals(PlayerMentalityComponent.class, m.getClass());
    }

    /**
     * Test for {@link TearWeaponComponent}, {@link TearAIComponent}.
     */
    @Test
    public void testTearWeaponComponent() {
        final double deltaTime = 2000;
        Player p = new Player();
        Room room = new RoomImpl(2, null);
        room.insertEntity(p);
        p.postEvent(new MoveEvent(p, 3, 2, 0));
        this.getMoveComponent(p).update(deltaTime);
        p.postEvent(new TearShotEvent(p, 90));

        assertEquals(room.getEntity().stream().filter(e -> e.getClass().equals(Tear.class)).count(), 1);
        Tear t = (Tear) room.getEntity().stream().filter(e -> e.getClass().equals(Tear.class)).findFirst().get();
        Triplet<Double, Double, Double> firstPosition = this.getBodyComponent(t).getPosition();
        assertEquals(firstPosition, this.getBodyComponent(p).getPosition());
        t.update(deltaTime);
        BodyComponent secondPosition = getBodyComponent(t);
        assertTrue(firstPosition.getV2() < secondPosition.getPosition().getV2());
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

    private InventoryComponent getInventoryComponent(final Entity e) {
        return (InventoryComponent) e.getComponent(InventoryComponent.class).get();
    }
}
