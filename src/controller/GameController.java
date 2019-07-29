package controller;


import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import model.game.GameWorld;
import model.util.EntityInformation;
import util.NotEquals;
import util.enumeration.BasicStatusEnum;

/**
 * The {@link Controller} for the game which includes the game loop and it
 * communicates with the View and checks the Model.
 *
 */
public class GameController extends AbstractController {
    private static  long timeToSleep = 33;
    @NotEquals
    private volatile boolean stoped;
    private final GameWorld gameWord;
    @NotEquals
    private final GameLoop gameloop;
    private final Map<UUID, EntityController> entityControllers;
    /**
     * 
     * @param main the {@link MainController}
     * @param gameWorld is game world
     */
    public GameController(final MainController main, final GameWorld gameWorld) {
        super(main);
        this.gameWord = gameWorld;
        this.stoped = false;
        this.gameloop = new GameLoop();
        this.entityControllers = new HashMap<UUID, EntityController>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        super.run();
        this.stoped = false;
        this.gameloop.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
       super.stop();
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
                        final EntityInformation dissapper = new EntityInformation().setStatus(BasicStatusEnum.DISAPPEAR);
                        entityControllers.values().stream().forEach(x -> { 
                                                         x.update(dissapper);
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
                                            entityControllers.put(st.getId(), new EntityController(st));
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
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
