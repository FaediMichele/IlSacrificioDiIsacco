//package controller.test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.List;
//import java.util.stream.Collectors;
//import org.junit.Test;
//import controller.EntityController;
//import javafx.scene.canvas.Canvas;
//import model.component.BodyComponent;
//import model.entity.GaperEnemy;
//import model.enumeration.BasicEntityEnum;
//import model.enumeration.BasicMovementEnum;
//import model.enumeration.BasicStatusEnum;
//import model.enumeration.BasicUpgradeEnum;
//import model.events.MoveEvent;
//import model.game.GameWorld;
//import model.game.GameWorldImpl;
//import model.game.Room;
//import model.util.EntityInformation;
//import view.javafx.game.GameView;
//import view.javafx.game.GameViewImpl;
//
///**
// * Test for the controller.
// */
//@SuppressWarnings("all")
//public class TestController {
//    /**
//     * @throws ClassNotFoundException 
//     * @throws IllegalAccessException 
//     * @throws InstantiationException 
//     * @throws InvocationTargetException 
//     * @throws IllegalArgumentException 
//     * @throws SecurityException 
//     * @throws NoSuchMethodException 
//     * 
//     */
//    @Test
//    public void testEntityController() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
//        final GameWorld gw = new GameWorldImpl("Game1");
//        final GameView gv = new GameViewImpl(new Canvas());
//        //final MainController main = new MainControllerImpl();
//        //final GameController gc = new GameController(gw);
//        //main.switchActive(gc);
//        //assertEquals(main.getActiveController(), gc);
//        Room room = gw.getActiveFloor().getActiveRoom();
//        assertEquals(room.getEntities().size(), 1);
//        GaperEnemy g = (GaperEnemy) room.getEntities().stream().filter(e -> e.getClass().equals(GaperEnemy.class)).findFirst().get();
//        g.postEvent(new MoveEvent(g, 2, 0, 0));
//        gw.update(1);
//        List<EntityInformation> info = room.getEntitiesStatus();
//        assertEquals(info.stream().filter(i -> i.getId().equals(g.getId())).collect(Collectors.toList()).size(), 1);
//        EntityInformation gI = info.stream().filter(i -> i.getId().equals(g.getId())).findFirst().get();
//        EntityController eC = new EntityController(gI, gv);
//        assertEquals(gI.getEntityName().getValue(), "util.enumeration.BasicEntityEnum.GAPER");
//        assertEquals(gI.getEntityName(), BasicEntityEnum.GAPER);
//        assertEquals(gI.getMove(), BasicMovementEnum.RIGHT);
//        assertEquals(gI.getPosition(), g.getComponent(BodyComponent.class).get().getPosition());
//        g.getStatusComponent().setStatus(BasicStatusEnum.DEAD);
//        gw.update(1);
//        info = room.getEntitiesStatus();
//        gI = info.stream().filter(i -> i.getId().equals(g.getId())).findFirst().get();
//        eC.update(gI);
//        g.getStatusComponent().addUpgrade(BasicUpgradeEnum.UPGRADETEST, "stringa", 1, 1.0, room);
//        gw.update(1);
//        info = room.getEntitiesStatus();
//        gI = info.stream().filter(i -> i.getId().equals(g.getId())).findFirst().get();
//        eC.update(gI);
//    }
//
//    /**
//     * Test game controller.
//     * @throws ClassNotFoundException 
//     * @throws IllegalAccessException 
//     * @throws InstantiationException 
//     */
////    @Test
////    public void testGameController() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
////        GameWorld gw = new GameWorldImpl("Game1");
////        Room room = gw.getActiveFloor().getActiveRoom();
////        GameController gc = new GameController(gw);
////        GaperEnemy g = (GaperEnemy) gw.getActiveFloor().getActiveRoom().getEntities().stream().filter(e -> e.getClass().equals(GaperEnemy.class)).findFirst().get();
////        g.getStatusComponent().addUpgrade(BasicUpgradeEnum.UPGRADETEST, "stringa", 1, 1.0, room);
////        g.postEvent(new MoveEvent(g, 2, 0, 0));
////        g.getStatusComponent().setStatus(BasicStatusEnum.DEAD);
////        gc.start();
////    }
//
//    /**
//     * 
//     *Class for test sleep.
//     *
//     */
//    private class TimeSleep extends Thread {
//        private final long time;
//
//        TimeSleep(final long time) {
//            this.time = time;
//        }
//        @Override
//        public void run() {
//            try {
//                for (long i = 0; i < this.time; i++) {
//                    this.sleep(1);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
