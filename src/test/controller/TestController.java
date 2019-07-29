package test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import controller.GameController;
import controller.MainController;
import controller.MainControllerImpl;
import model.entity.GaperEnemy;
import model.events.MoveEvent;
import model.game.GameWorld;
import model.game.GameWorldImpl;
import model.game.Room;

/**
 * Test for the controller.
 */
@SuppressWarnings("all")
public class TestController {
    /**
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * 
     */
    @Test
    public void testGameController() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        final GameWorld gw = new GameWorldImpl("Game1");
        final MainController main = new MainControllerImpl();
        final GameController gc = new GameController(main, gw);
        main.switchActive(gc);
        assertEquals(main.getActiveController(), gc);
        Room room = gw.getActiveFloor().getActiveRoom();
        room.getEntities().forEach(e -> System.out.println(e.getClass()));
        assertEquals(room.getEntities().size(), 5);
        GaperEnemy g = (GaperEnemy) room.getEntities().stream().filter(e -> e.getClass().equals(GaperEnemy.class)).findFirst().get();
        g.postEvent(new MoveEvent(g, 2, 2, 0));
    }
}
