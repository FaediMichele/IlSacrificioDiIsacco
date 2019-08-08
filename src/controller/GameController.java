package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import model.entity.FactoryPlayersUtil;
import model.enumeration.BasicStatusEnum;
import model.enumeration.PlayerEnum;
import model.game.GameWorld;
import model.game.GameWorldImpl;
import model.util.DataPlayer;
import model.util.EntityInformation;
import model.util.Position;
import util.Command;
import util.NotEquals;
import view.javafx.game.GameView;
import view.javafx.game.RoomView;

/**
 * The {@link Controller} for the game which includes the game loop and it
 * communicates with the View and checks the Model.
 *
 */
public class GameController {
    private static final double PADDING_X_MAP = 51;
    private static final double PADDING_Y_MAP = 51;
    private static  long timeToSleep = 500;
    @NotEquals
    private volatile boolean stoped;
    private final GameWorld gameWord;
    @NotEquals
    private final GameLoop gameloop;
    private final GameView gameView;
    private final Map<UUID, EntityController> entityControllers;
    private final Semaphore inputDisponible = new Semaphore(1);
    private final PriorityQueue<Command> inputCommand = new PriorityQueue<>();

    /**
     * @param gameView is the {@link GameView} in which the Game Controller operates
     * @param player .
     * @param game .
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws IOException 
     */
    public GameController(final GameView gameView, final PlayerEnum player, final String game) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
        this.gameView = gameView;
        this.gameWord = new GameWorldImpl(game, FactoryPlayersUtil.getPlayer(player));
        this.stoped = false;
        this.gameloop = new GameLoop();
        this.entityControllers = new HashMap<UUID, EntityController>();
//        Set<? extends Door> doors = this.gameWord.getActiveFloor().getActiveRoom().getDoor();
//        List<EntityView> doorsView = new ArrayList<>();
//        for (final Door door : doors ) {
//            final EntityView doorView = 
//        }
        gameView.setRoomView(new RoomView("/gameImgs/basement_background1.png", null));
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
                    //Se il Player è morto -> gameView.gameOver()
                    final double widthMolti = gameView.getWidth() 
                                               / gameWord.getActiveFloor().getActiveRoom().getWidth();
                    final double heightMolti = gameView.getHeight() 
                                               / gameWord.getActiveFloor().getActiveRoom().getHeight();
                    final double heightAdd = gameView.getHeight() - PADDING_Y_MAP;
                    if (gameWord.isChangeFloor() || gameWord.getActiveFloor().isChangeRoom()) {
                        final EntityInformation disappear = new EntityInformation().setStatus(BasicStatusEnum.DISAPPEAR);
                        entityControllers.values().stream().forEach(x -> {
                                                         x.update(disappear);
                                                         entityControllers.remove(x.getId());
                                                      });
                    }
                    gameWord.getEntityInformation()
                            .stream()
                            .peek(i -> 
                                i.setWidth(i.getWidth() * widthMolti)
                                 .setHeight(i.getHeight() * heightMolti)
                                 .setPosition(new Position(PADDING_X_MAP + i.getPosition().getX()  * widthMolti, 
                                         heightAdd - i.getPosition().getY() * heightMolti, 
                                                           i.getPosition().getZ()))
                                 )
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
                    //gestione StatisticView da fare
                    gameView.draw();
                    if (inputDisponible.tryAcquire()) {
                        inputCommand.forEach(c -> gameWord.input(c));
                        inputCommand.clear();
                        inputDisponible.release();
                    }
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
    public static DataPlayer getDataPlayer(final PlayerEnum plEnumMenu) throws ClassNotFoundException {
        return FactoryPlayersUtil.getDataPlayer(plEnumMenu);
    }

    /**
     * 
     * @param cm .
     */
    public void input(final Command cm) {
        try {
            inputDisponible.acquire();
            inputCommand.add(cm);
            inputDisponible.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
