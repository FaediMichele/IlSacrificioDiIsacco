package test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import controller.EntityController;
import controller.GameController;
import controller.MainController;
import controller.MainControllerImpl;
import model.component.BodyComponent;
import model.entity.GaperEnemy;
import model.events.MoveEvent;
import model.game.GameWorld;
import model.game.GameWorldImpl;
import model.game.Room;
import model.util.EntityInformation;
import util.enumeration.BasicEntityEnum;
import util.enumeration.BasicMovementEnum;
import util.enumeration.BasicStatusEnum;

/**
 * Test for the controller.
 */
@SuppressWarnings("all")
public class TestController {
    /**
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * 
     */
    @Test
    public void testGameController() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        final GameWorld gw = new GameWorldImpl("Game1");
        final MainController main = new MainControllerImpl();
        final GameController gc = new GameController(main, gw);
        main.switchActive(gc);
        assertEquals(main.getActiveController(), gc);
        Room room = gw.getActiveFloor().getActiveRoom();
        assertEquals(room.getEntities().size(), 5);
        GaperEnemy g = (GaperEnemy) room.getEntities().stream().filter(e -> e.getClass().equals(GaperEnemy.class)).findFirst().get();
        g.postEvent(new MoveEvent(g, 2, 0, 0));
        gw.update(1);
        List<EntityInformation> info = room.getEntitiesStatus();
        assertEquals(info.stream().filter(i -> i.getId().equals(g.getId())).collect(Collectors.toList()).size(), 1);
        EntityInformation gI = info.stream().filter(i -> i.getId().equals(g.getId())).findFirst().get();
        EntityController eC = new EntityController();
        assertEquals(gI.getEntityName().getValue(), "util.enumeration.BasicEntityEnum.GAPER");
        assertEquals(gI.getEntityName(), BasicEntityEnum.GAPER);
        assertEquals(gI.getMove(), BasicMovementEnum.UP);
        assertEquals(gI.getPosition(), g.getComponent(BodyComponent.class).get().getPosition());
        g.getStatusComponent().setStatus(BasicStatusEnum.DEAD);
        gw.update(1);
        info = room.getEntitiesStatus();
        gI = info.stream().filter(i -> i.getId().equals(g.getId())).findFirst().get();
        //eC.update(gI);
    }
}
