package model.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import model.component.DamageComponent;
import model.component.EnemyHealthComponent;
import model.component.InventoryComponent;
import model.component.PlayerHealthComponent;
import model.component.mentality.AbstractMentalityComponent;
import model.component.mentality.EnemyMentalityComponent;
import model.component.mentality.PlayerMentalityComponent;
import model.entity.BlackPickupableHeart;
import model.entity.Bomb;
import model.entity.FactoryPlayersUtils;
import model.entity.GaperEnemy;
import model.entity.Key;
import model.entity.Player;
import model.entity.SimplePsychopathEntity;
import model.enumeration.BasicPlayerEnum;
import model.events.CollisionEvent;
import model.events.DamageEvent;
import model.game.Room;
import model.game.RoomImpl;

/**
 * Test in JUnit for the package model.game.
 * 
 */
public class TestModel {

    private static final double RESET_DAMAGE_PLAYER = 2000.0;

    /**
     * test for mentality and damage.
     * @throws ClassNotFoundException 
     */
        @Test
      public void testMentality() throws ClassNotFoundException {
          final Player player = FactoryPlayersUtils.getPlayer(BasicPlayerEnum.ISAAC);
          final Player player1 = FactoryPlayersUtils.getPlayer(BasicPlayerEnum.CAIN);
          final GaperEnemy enemy = new GaperEnemy();
          final SimplePsychopathEntity psychoEntity = new SimplePsychopathEntity();
          assertEquals(enemy.getComponent(AbstractMentalityComponent.class).get().getClass(), 
                          EnemyMentalityComponent.class, 
                          "Test mentality enemy is right");
          assertEquals(player.getComponent(AbstractMentalityComponent.class).get().getClass(), 
                  PlayerMentalityComponent.class, 
                  "Test player enemy is right");
          final double damage = enemy.getComponent(DamageComponent.class).get().getDamage();
          final double life = player.getComponent(PlayerHealthComponent.class).get().getLife();
          player.postEvent(new CollisionEvent(enemy));
          assertEquals(life - damage, 
                          player.getComponent(PlayerHealthComponent.class).get().getLife(),
                          "Test if player is damaging");
          player.postEvent(new CollisionEvent(player1));
          assertEquals(life - damage, 
                  player.getComponent(PlayerHealthComponent.class).get().getLife(),
                  "Test if player is not damaging");
          final double damage2 = psychoEntity.getComponent(DamageComponent.class).get().getDamage();
          player.update(RESET_DAMAGE_PLAYER);
          player.postEvent(new CollisionEvent(psychoEntity));
          assertEquals(life - damage - damage2, 
                          player.getComponent(PlayerHealthComponent.class).get().getLife(),
                          "Test if player is damaging");
      }

      /**
       * Test for collectible entities.
     * @throws ClassNotFoundException 
       */
      @Test
      public void testPickupableCollectable() throws ClassNotFoundException {
          final Room room = new RoomImpl(0, 100, 100);
          final GaperEnemy enemy = new GaperEnemy();
          final Player player = FactoryPlayersUtils.getPlayer(BasicPlayerEnum.ISAAC);
          final Bomb bomb = new Bomb();
          final Key key = new Key();
          final BlackPickupableHeart blHeart = new BlackPickupableHeart();
          room.insertEntity(key)
              .insertEntity(bomb)
              .insertEntity(player)
              .insertEntity(blHeart)
              .insertEntity(enemy);
          room.updateEntityList();
          assertEquals(5, room.getEntities().size(), "I verify that all the entities have been added");
          assertTrue("Check if the room contains the key", room.getEntities().contains(key));
          assertTrue("Check if the room contains the bomb", room.getEntities().contains(bomb));
          assertTrue("Check if the room contains the player", room.getEntities().contains(player));
          assertTrue("Check if the room contains the blHeart", room.getEntities().contains(blHeart));
          final InventoryComponent inventoryComponent = player.getComponent(InventoryComponent.class).get();
          assertTrue("Check if the inventory component has collected zero entities", inventoryComponent.getThings().isEmpty());
          player.postEvent(new CollisionEvent(key));
          assertEquals(inventoryComponent.getThings().size(), 1, "Check that all entities have been collected");
          player.postEvent(new CollisionEvent(bomb));
          assertEquals(inventoryComponent.getThings().size(), 2, "Check that all entities have been collected");
          final double life = player.getComponent(PlayerHealthComponent.class).get().getLife();
          player.postEvent(new CollisionEvent(blHeart));
          room.updateEntityList();
          final double lifeEnemy  = enemy.getComponent(EnemyHealthComponent.class).get().getLife();
          assertEquals(2, room.getEntities().size(), "I verify that all the entities have been remove");
          assertTrue("Check if the life of the player has risen", life < player.getComponent(PlayerHealthComponent.class).get().getLife());
          player.postEvent(new DamageEvent(enemy, 3));
          assertTrue("Check black heart damage enemies", lifeEnemy > enemy.getComponent(EnemyHealthComponent.class).get().getLife());
      }

      /**
       * Testing for load data player from file XML with a {@link PlayerEnum}.
     * @throws ClassNotFoundException 
       */
      @Test
      public void testDataPlayerToXml() throws ClassNotFoundException {
          final String dataPlayerIsac = "name     = " + "ISAAC"     + "\n" 
                                  + "life     = " + "3.5" + "\n" 
                                  + "speed    = " + "1.8" + "\n" 
                                  + "damage   = " + "1.0" + "\n" 
                                  + "tearRate = " + "200.0" + "\n";
          assertEquals(dataPlayerIsac, FactoryPlayersUtils.getDataPlayer(BasicPlayerEnum.ISAAC).toString(), 
                                      "Verify that the correct DataPlayer is returned");
      }
//
//    private Room buildedRoom;
//
//    /**
//     * Test for {@link GameWorld}.
//     * @throws ClassNotFoundException 
//     * @throws IllegalAccessException 
//     * @throws InstantiationException 
//     */
//    @Test
//    public void testGameWorld() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//        final GameWorld gw = new GameWorldImpl("game1", StasticFactoryPlayersUtils.getPlayer(BasicPlayerEnum.ISAAC));
//    }
//
//    /**
//     * Test for {@link Entity}.
//     */
//    @Test
//    public void testEntity() {
//        final Entity p = new Player();
//        final Entity p2 = new Player();
//        final Entity f = new Fire(FireType.RED, 0, 0);
//        final Entity d1 = new Door(0, 0);
//        final Entity d2 = new Door(0, 0);
//        p2.attachComponent(new BodyComponent(p2, 1, 1, 0, 1, 1, 2));
//        p.attachComponent(new BodyComponent(p, 1, 1, 0, 1, 1, 2));
//        assertTrue(p.hasComponent(BodyComponent.class));
//        assertTrue(p.hasComponent(PlayerHealthComponent.class));
//        assertFalse(p.hasComponent(DoorAIComponent.class));
//        assertEquals(p, p2);
//        assertTrue(p.equals(p2));
//        assertFalse(p.equals(f));
//        assertEquals(d1, d2);
//        assertFalse(p.equals(d1));
//        assertEquals(p.getComponent(BodyComponent.class).get(), new BodyComponent(p, 1, 1, 0, 1, 1, 2));
//        assertTrue(p.getComponent(BodyComponent.class).get().equals(new BodyComponent(p2, 1, 1, 0, 1, 1, 2)));
//    }
//
//    /**
//     * Test for {@link Fire}.
//     */
//    @Test
//    public void testFire() {
//        final Fire f1 = new Fire(FireType.RED, 0, 0);
//        final Fire f2 = new Fire(FireType.BLUE, 0, 0);
//        assertEquals(f1.getComponent(FireAIComponent.class).get(), new FireAIComponent(f1, FireType.RED));
//        f1.postEvent(new FireHittedEvent(f1));
//        assertFalse(f1.getComponent(FireAIComponent.class).get().equals(new FireAIComponent(f1, FireType.RED)));
//        assertEquals(
//                Integer.valueOf(FireAIComponent.class.cast(f1.getComponent(FireAIComponent.class).get()).getLife()),
//                Integer.valueOf(3));
//        assertEquals(
//                Integer.valueOf(FireAIComponent.class.cast(f2.getComponent(FireAIComponent.class).get()).getLife()),
//                Integer.valueOf(4));
//        f2.postEvent(new FireHittedEvent(f2));
//        f2.postEvent(new FireHittedEvent(f2));
//        assertEquals(
//                Integer.valueOf(FireAIComponent.class.cast(f1.getComponent(FireAIComponent.class).get()).getLife()),
//                Integer.valueOf(3));
//        assertEquals(
//                Integer.valueOf(FireAIComponent.class.cast(f2.getComponent(FireAIComponent.class).get()).getLife()),
//                Integer.valueOf(2));
//        // (f1.getComponent(FireComponent.class).get()).dispose();
//    }
//
//    /**
//     * Test for the floor.
//     */
//    @Test
//    public void testFloor() {
//        final List<Room> rooms = new ArrayList<>();
//        boolean ok;
//        Door d1 = new Door(2, 0);
//        Door d2 = new Door(1, 2);
//        rooms.add(new RoomImpl(0, new ArrayList<>(Arrays.asList(new Door(0, 1), new Door(1, 2))), 10, 10));
//        rooms.add(new RoomImpl(1, new ArrayList<>(Arrays.asList(d1, d2)), 10, 10));
//        rooms.add(new RoomImpl(2, new ArrayList<>(Arrays.asList(new Door(3, 0), new Door(1, 1))), 10, 10));
//        final Floor f = new FloorImpl(rooms);
//
//        assertEquals(Integer.valueOf(f.getActiveRoom().getIndex()), Integer.valueOf(0));
//        assertThrows(IllegalArgumentException.class, () -> f.changeRoom(-1));
//
//        try {
//            f.changeRoom(1);
//        } catch (Exception e) {
//            ok = false;
//        } finally {
//            ok = true;
//        }
//        assertTrue(ok);
//        rooms.forEach(r -> assertEquals(r.getFloor(), f));
//        assertEquals(f.getActiveRoom(), rooms.get(1));
//        assertTrue(f.getActiveRoom().getDoor().containsAll(Arrays.asList(d1, d2)));
//        assertThrows(IllegalStateException.class, () -> rooms.get(0).setFloor(new FloorImpl()));
//    }
//
//    /**
//     * Test for the room that contains entity.
//     */
//    @Test
//    public void testRoom() {
//        final List<Entity> e = new ArrayList<>();
//        final Rock r = new Rock();
//        final Fire f = new Fire(FireType.RED, 0, 0);
//        e.add(r);
//        e.add(f);
//        final Room room = new RoomImpl(0, new ArrayList<Door>(), e, 10, 10);
//        e.forEach(entity -> assertEquals(entity.getRoom(), room));
//
//        final List<Entity> e1 = new ArrayList<>();
//        e1.add(r);
//        e1.add(f);
//        assertTrue(room.getEntities().containsAll(e1));
//        room.updateEntity(0.0);
//        room.updateEntity(10.0);
//        assertTrue(room.getEntities().containsAll(e1));
//    }
//
//    /**
//     * Test for the collision. It require many time to build the space.
//     */
//    @Test
//    public void testCollision() {
//        final List<Entity> e = new ArrayList<>();
//        e.add(new Rock(100, 100));
//        e.add(new Fire(FireType.RED, 64, 64));
//        Entity npc1 = createNPC(0, 0);
//        Entity npc2 = createNPC(10, 10);
//        e.add(npc1);
//        e.add(npc2);
//        BodyComponent b = getBodyComponent(npc2);
//        buildedRoom = new RoomImpl(0, new ArrayList<Door>(), e, 10, 10);
//        Set<Pair<Entity, Entity>> coll = buildedRoom.getEntityColliding();
//        assertTrue(coll.size() == 0);
//        b.setPosition(0, 4, 4);
//        buildedRoom.calculateCollision();
//
//        assertTrue(buildedRoom.getEntityColliding().size() == 1);
//        buildedRoom.updateEntity(90.0);
//        buildedRoom.calculateCollision();
//        coll = buildedRoom.getEntityColliding();
//        assertTrue(coll.stream()
//                .filter(p -> Double.isNaN(getBodyComponent(p.getX()).getPosition().getX())
//                        || Double.isNaN(getBodyComponent(p.getX()).getPosition().getY())
//                        || Double.isNaN(getBodyComponent(p.getX()).getPosition().getZ())
//                        || Double.isNaN(getBodyComponent(p.getY()).getPosition().getX())
//                        || Double.isNaN(getBodyComponent(p.getY()).getPosition().getY())
//                        || Double.isNaN(getBodyComponent(p.getY()).getPosition().getZ()))
//                .count() == 0);
//        assertTrue(buildedRoom.getEntityColliding().size() == 0);
//    }
//
//    /**
//     * Test for the time of the collision detection. The first time is not counted.
//     */
//    @Test
//    public void timeForCollision() {
//        testCollision();
//        int time = 10;
//        long startTime = System.nanoTime();
//        for (int i = 0; i < time; i++) {
//            buildedRoom.calculateCollision();
//            buildedRoom.updateEntity(1.0);
//        }
//        long endTime = System.nanoTime();
//        long timeElapsed = (endTime - startTime) / (100 * 100 * 100); // to avoid checkstyle
//        assertTrue(timeElapsed < 3 * 10);
//    }
//
//    /**
//     * Test for the creation of an NPC.
//     */
//    @Test
//    public void createNPC() {
//        Entity ret = createNPC(0, 0);
//        assertNotNull(ret);
//        assertNotNull(ret.getStatusComponent());
//        assertNotNull(ret.getComponent(BodyComponent.class).get());
//        assertNotNull(ret.getComponent(AbstractAIComponent.class).get());
//        assertNotNull(ret.getComponent(CollisionComponent.class).get());
//    }
//
//    private Entity createNPC(final double x, final double y) {
//        return new GaperEnemy(x, y);
//    }
//
//    /**
//     * Test for {@link MoveComponent}.
//     */
//    @Test
//    public void testMoveComponent() {
//        final double randomTime = 10;
//        final Entity p = new Player();
//        p.attachComponent(new BodyComponent(p));
//        p.attachComponent(new MoveComponent(p));
//        p.postEvent(new MoveEvent(p, 2, 0, -1));
//        assertEquals(getMoveComponent(p).getxMove(), 2);
//        assertEquals(getMoveComponent(p).getyMove(), MoveComponent.NOMOVE);
//        assertEquals(getMoveComponent(p).getzMove(), -1);
//        getMoveComponent(p).update(randomTime);
//        assertTrue(getBodyComponent(p).getPosition().getX() > 0.0);
//        assertTrue(getBodyComponent(p).getPosition().getZ() < 0.0);
//        assertEquals(getBodyComponent(p).getPosition().getY(), Double.valueOf(0));
//        assertEquals(getMoveComponent(p).getxMove(), MoveComponent.NOMOVE);
//        assertEquals(getMoveComponent(p).getyMove(), MoveComponent.NOMOVE);
//        assertEquals(getMoveComponent(p).getzMove(), MoveComponent.NOMOVE);
//    }
//
//    /**
//     * Test for {@link PlayerHealthComponent}.
//     */
//    @Test
//    public void testHealthComponent() {
//        final double defaultHearts = 3;
//        final Entity goodEntity = new Player();
//        goodEntity.attachComponent(new PlayerHealthComponent(goodEntity));
//        assertTrue(this.getHealthComponent(goodEntity).isAlive());
//        assertEquals(this.getHealthComponent(goodEntity).getLife(), defaultHearts);
//
//        final Entity firstEnemy = new Player();
//        final double firstDamage = 0.4;
//        firstEnemy.attachComponent(new DamageComponent(firstEnemy, firstDamage));
//        goodEntity.postEvent(new DamageEvent(firstEnemy));
//        assertEquals(this.getHealthComponent(goodEntity).getLife(), defaultHearts - firstDamage);
//        assertEquals(this.getHealthComponent(goodEntity).getNumberOfHearts(), 3);
//
//        final Entity secondEnemy = new Player();
//        final double secondDamage = 1.5;
//        secondEnemy.attachComponent(new DamageComponent(secondEnemy, secondDamage));
//        goodEntity.postEvent(new DamageEvent(secondEnemy));
//        assertEquals(this.getHealthComponent(goodEntity).getNumberOfHearts(), 2);
//        assertTrue(this.getHealthComponent(goodEntity).isAlive());
//        assertEquals(this.getHealthComponent(goodEntity).getLife(), defaultHearts - firstDamage - secondDamage);
//
//        final Entity thirdEnemy = new Player();
//        final double thirdDamage = 0.1;
//        thirdEnemy.attachComponent(new DamageComponent(thirdEnemy, thirdDamage));
//        goodEntity.postEvent(new DamageEvent(thirdEnemy));
//        assertTrue(this.getHealthComponent(goodEntity).isAlive());
//        assertEquals(this.getHealthComponent(goodEntity).getLife(), 1);
//        assertEquals(this.getHealthComponent(goodEntity).getNumberOfHearts(), 1);
//
//        final Entity fourthEnemy = new Player();
//        final double fourthDamage = 1.0;
//        fourthEnemy.attachComponent(new DamageComponent(fourthEnemy, fourthDamage));
//        goodEntity.postEvent(new DamageEvent(fourthEnemy));
//        assertFalse(this.getHealthComponent(goodEntity).isAlive());
//        assertEquals(this.getHealthComponent(goodEntity).getLife(), 0);
//    }
//
//    /**
//     * Test for {@link BlackHeart}.
//     */
//    @Test
//    public void testBlackHeart() {
//        final int defaultHearts = 3;
//        final int newHearts = 2;
//        final Entity goodEntity = new Player();
//        goodEntity.attachComponent(new PlayerHealthComponent(goodEntity, defaultHearts));
//        this.getHealthComponent(goodEntity).addHeart(new SimpleHeart());
//        this.getHealthComponent(goodEntity).addHeart(new BlackHeart.Builder(goodEntity).build());
//        assertTrue(this.getHealthComponent(goodEntity).isAlive());
//        assertEquals(this.getHealthComponent(goodEntity).getLife(), defaultHearts + newHearts);
//
//        final Entity enemy = new Player();
//        final double damage = 0.7;
//        final double finalEnemyLife = 2.7;
//        enemy.attachComponent(new DamageComponent(enemy, damage));
//        enemy.attachComponent(new PlayerHealthComponent(enemy));
//        enemy.attachComponent(new EnemyMentalityComponent(enemy));
//        goodEntity.postEvent(new DamageEvent(enemy));
//        assertEquals(this.getHealthComponent(enemy).getNumberOfHearts(), 3);
//        assertEquals(this.getHealthComponent(enemy).getLife(), 3);
//
//        final List<Entity> entities = new ArrayList<>();
//        entities.add(goodEntity);
//        entities.add(enemy);
//        new RoomImpl(0, new LinkedList<Door>(), entities, 10, 10);
//        goodEntity.postEvent(new DamageEvent(enemy));
//        assertEquals(this.getHealthComponent(enemy).getNumberOfHearts(), 3);
//        assertEquals(this.getHealthComponent(enemy).getLife(), finalEnemyLife);
//        assertEquals(this.getHealthComponent(goodEntity).getNumberOfHearts(), defaultHearts + newHearts - 1);
//    }
//
//    /**
//     * Test for {@link CollisionComponent}.
//     */
//    @Test
//    public void testCollisionComponent() {
//        final Entity player = new Player();
//        final Entity good = new Player();
//        final Entity enemy = new SimpleEnemyMovable();
//        final Entity collectible = new Player();
//        collectible.attachComponent(new BombCollectableComponent(collectible, 1, 1, 1));
//        final double damage = 0.2;
//        final double settingBombTest = 0.5;
//        final int numberOfThings = 1;
//        double life = getHealthComponent(player).getLife();
//
//        enemy.attachComponent(new DamageComponent(enemy, damage));
//        player.postEvent(new CollisionEvent(enemy));
//        assertEquals(life - damage, getHealthComponent(player).getLife());
//
//        good.attachComponent(new DamageComponent(good, damage));
//        player.postEvent(new CollisionEvent(good));
//        assertEquals(life - damage, getHealthComponent(player).getLife());
//
//        Room room = new RoomImpl(2, null, 10, 10);
//        room.insertEntity(collectible);
//        assertTrue(player.hasComponent(StatusComponent.class));
//        assertTrue(collectible.hasComponent(StatusComponent.class));
//        player.postEvent(new CollisionEvent(collectible));
//
//        assertTrue(collectible.hasComponent(AbstractPickupableComponent.class));
//        assertEquals(numberOfThings, getInventoryComponent(player).getThings().size());
//        assertEquals(0, room.getEntities().size());
//    }
//
//    /**
//     * Test for {@link StatusComponent}.
//     */
//    @Test
//    public void testStatusComponent() {
////        Entity entity = new Player();
////        entity.getStatusComponent().setStatus(new Pair<>(1, "pick up"));
////        assertEquals("pick up",   entity.getStatusComponent().getStatus().get(0));
//    }
//
//    /**
//     * Test for {@link InventoryComponent}.
//     */
//    /*
//     * @Test public void testInventoryComponent() { final Player player = new
//     * Player(); final Bomb bomb = new Bomb();
//     * 
//     * Room room = new RoomImpl(2, null); room.insertEntity(player);
//     * room.insertEntity(bomb); assertEquals(room.getEntity().size(), 2);
//     * 
//     * player.postEvent(new CollisionEvent(bomb)); assertEquals(1,
//     * getInventoryComponent(player).getThings().size());
//     * assertTrue(getInventoryComponent(player).getThings().contains(bomb));
//     * 
//     * final Bomb bomb2 = new Bomb(); room.insertEntity(bomb2); player.postEvent(new
//     * CollisionEvent(bomb2)); assertEquals(true,
//     * bomb2.hasComponent(AbstractPickupableComponent.class)); assertEquals(2,
//     * getInventoryComponent(player).getThings().size());
//     * assertTrue(getInventoryComponent(player).getThings().contains(bomb2));
//     * 
//     * player.postEvent(new UseThingEvent(player, bomb.getClass())); assertEquals(1,
//     * getInventoryComponent(player).getThings().size());
//     * assertTrue(getInventoryComponent(player).getThings().contains(bomb2));
//     * assertEquals(room.getEntity().size(), 2);
//     * assertTrue(room.getEntity().contains(bomb));
//     * assertEquals(getBodyComponent(player).getPosition(),
//     * getBodyComponent(bomb).getPosition());
//     * 
//     * final Heart heart = new Heart(); room.insertEntity(heart);
//     * assertEquals(getHealthComponent(player).getHearts().size(), 3);
//     * player.postEvent(new CollisionEvent(heart));
//     * assertEquals(getHealthComponent(player).getHearts().size(), 4);
//     * 
//     * final Key key = new Key(); room.insertEntity(key); player.postEvent(new
//     * CollisionEvent(key)); assertEquals(2,
//     * getInventoryComponent(player).getThings().size());
//     * assertTrue(getInventoryComponent(player).getThings().contains(key));
//     * 
//     * player.postEvent(new UseThingEvent(player, key.getClass())); assertEquals(1,
//     * getInventoryComponent(player).getThings().size());
//     * assertFalse(room.getEntity().contains(key));
//     * 
//     * final List<Room> rooms = new ArrayList<>(); final Door door = new Door(0, 1);
//     * final Room r = new RoomImpl(0, new ArrayList<>(Arrays.asList(door)));
//     * rooms.add(r); rooms.add(new RoomImpl(1, new ArrayList<>(Arrays.asList(new
//     * Door(2, 0))))); final Floor f = new FloorImpl(rooms); f.getRooms();
//     * 
//     * r.insertEntity(player); r.insertEntity(key); player.postEvent(new
//     * CollisionEvent(key)); assertEquals(2,
//     * getInventoryComponent(player).getThings().size()); door.postEvent(new
//     * CollisionEvent(player)); assertEquals(1,
//     * getInventoryComponent(player).getThings().size());
//     * assertFalse(room.getEntity().contains(key)); }
//     */
//
//    /**
//     * Test for {@link InventoryComponent}.
//     */
//    @Test
//    public void testMentalityComponent() {
//        Player p = new Player();
//        AbstractMentalityComponent m = p.getComponent(AbstractMentalityComponent.class).get();
//        assertEquals(PlayerMentalityComponent.class, m.getClass());
//    }
//
//    /**
//     * Test for {@link TearWeaponComponent}, {@link TearAIComponent}.
//     */
//    @Test
//    public void testTearWeaponComponent() {
//        final double deltaTime = 2000;
//        Player p = new Player();
//        Room room = new RoomImpl(2, null, 10, 10);
//        room.insertEntity(p);
//        p.postEvent(new MoveEvent(p, 3, 2, 0));
//        this.getMoveComponent(p).update(deltaTime);
//        p.postEvent(new TearShotEvent(p, 90));
//
//        assertEquals(room.getEntities().stream().filter(e -> e.getClass().equals(Tear.class)).count(), 1);
//        Tear t = (Tear) room.getEntities().stream().filter(e -> e.getClass().equals(Tear.class)).findFirst().get();
//        Triplet<Double, Double, Double> firstPosition = this.getBodyComponent(t).getPosition();
//        assertEquals(firstPosition, this.getBodyComponent(p).getPosition());
//        t.update(deltaTime);
//        BodyComponent secondPosition = getBodyComponent(t);
//        assertTrue(firstPosition.getY() < secondPosition.getPosition().getY());
//    }
//
//    /**
//     * Test for the map.
//     */
//    @Test
//    public void testMap() {
//        Map<Integer, String> map = new HashMap<>();
//        map.put(1, "uno");
//        assertEquals("uno", map.get(1));
//        map.put(1, "due");
//        assertEquals("due", map.get(1));
//    }
//
//    private PlayerHealthComponent getHealthComponent(final Entity e) {
//        return e.getComponent(PlayerHealthComponent.class).get();
//    }
//
//    private MoveComponent getMoveComponent(final Entity e) {
//        return e.getComponent(MoveComponent.class).get();
//    }
//
//    private BodyComponent getBodyComponent(final Entity e) {
//        return e.getComponent(BodyComponent.class).get();
//    }
//
//    private InventoryComponent getInventoryComponent(final Entity e) {
//        return e.getComponent(InventoryComponent.class).get();
//    }
}
