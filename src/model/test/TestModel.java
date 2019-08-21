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
}
