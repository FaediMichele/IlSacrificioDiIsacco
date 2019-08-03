package controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import model.entity.FactoryPlayers;
import model.enumeration.BasicStatusEnum;
import model.enumeration.PlayerEnum;
import model.game.GameWorld;
import model.util.EntityInformation;
import util.NotEquals;
import util.StaticMethodsUtils;
import view.enumeration.PlayerMenuEnum;
import view.javafx.game.GameView;
import view.javafx.game.GameViewImpl;
import view.util.DataPlayer;


/**
 * The {@link Controller} for the game which includes the game loop and it
 * communicates with the View and checks the Model.
 *
 */
public class GameController {
    private static String pathXml = "/xml/controller/formEnumViewToEnumModel.xml";
    private static  long timeToSleep = 33;
    @NotEquals
    private volatile boolean stoped;
    private final GameWorld gameWord;
    @NotEquals
    private final GameLoop gameloop;
    private final GameView gameView;
    private final Map<UUID, EntityController> entityControllers;
    /**
     * 
     * @param gameWorld is game world
     */
    public GameController(final GameWorld gameWorld /*, final SubMenuGame gameMenu*/) {
        //super(main);
        //this.gameView = gameMenu.getGameView();
        this.gameView = new GameViewImpl();
        this.gameWord = gameWorld;
        this.stoped = false;
        this.gameloop = new GameLoop();
        this.entityControllers = new HashMap<UUID, EntityController>();
    }

    /**
     * {@inheritDoc}
     */
    public void start() {
       // super.run();
        this.stoped = false;
        this.gameloop.start();
    }

    /**
     * {@inheritDoc}
     */
    public void stop() {
      // super.stop();
       this.stoped = true;
    }
    /**
     * 
     * Is thread for game loop for game.
     *
     */
    private class GameLoop extends Thread {

        /**
         * is run.
         */
        @Override
        public void run() {
            try {
                while (!stoped) {
                    sleep(timeToSleep);
                    gameWord.update(timeToSleep);
                    if (gameWord.isChangeFloor() || gameWord.getActiveFloor().isChangeRoom()) {
                        final EntityInformation disappear = new EntityInformation().setStatus(BasicStatusEnum.DISAPPEAR);
                        entityControllers.values().stream().forEach(x -> { 
                                                         x.update(disappear);
                                                         entityControllers.remove(x.getId());
                                                      });
                    }
                    gameWord.getActiveFloor()
                            .getActiveRoom()
                            .getEntitiesStatus()
                            .stream()
                            .peek(st -> {
                                    if (!entityControllers.containsKey(st.getId())) {
                                        try {
                                            entityControllers.put(st.getId(), new EntityController(st, gameView));
                                        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException
                                                | InstantiationException | IllegalAccessException
                                                | IllegalArgumentException | InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                            .forEach(st -> {
                                    entityControllers.get(st.getId()).update(st);
                                    if (st.getStatus().equals(BasicStatusEnum.DISAPPEAR) || st.getStatus().equals(BasicStatusEnum.DEAD)) {
                                        entityControllers.remove(st.getId());
                                    }
                                });
                    //gameView.draw();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @param plEnumMenu .
     * @return .
     * @throws ClassNotFoundException 
     */
    public static DataPlayer getDataPlayer(final PlayerMenuEnum plEnumMenu) throws ClassNotFoundException {
        PlayerEnum plEnum = StaticMethodsUtils.enumFromViewToModel(plEnumMenu, pathXml);
        model.util.DataPlayer dataPlayerModel = FactoryPlayers.getDataPlayer(plEnum);
        return new view.util.DataPlayer()
                            .setDamage(dataPlayerModel.getDamage())
                            .setLife(dataPlayerModel.getLife())
                            .setSpeed(dataPlayerModel.getSpeed());
    }
    
    /*devo sparare la comand nel game word */
}
