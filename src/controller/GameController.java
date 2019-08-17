package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import model.entity.FactoryPlayersUtils;
import model.enumeration.BasicMovementEnum;
import model.enumeration.BasicStatusEnum;
import model.enumeration.GameEndStatus;
import model.enumeration.PlayerEnum;
import model.game.GameWorld;
import model.game.GameWorldImpl;
import model.util.DataPlayer;
import model.util.EntityInformation;
import model.util.Position;
import model.util.StatisticsInformations;
import util.Command;
import util.Lambdas;
import util.NotEquals;
import view.javafx.game.GameView;
import view.javafx.game.HeartStatisticView;
import view.javafx.game.KeyStatisticView;
import view.javafx.game.BombStatisticView;
import view.javafx.game.RoomView;

/**
 * is the part of the controller that communicates model view and contains and
 * starts the game loop of the game.
 *
 */
public class GameController {
    private static final long TIMETOSLEEP = 50;

    @NotEquals
    private volatile boolean stopped;
    private final GameWorld gameWorld;
    @NotEquals
    private final GameLoop gameloop;
    private final GameView gameView;
    private final Map<UUID, EntityController> entityControllers;
    private final Semaphore inputDisponible = new Semaphore(1);
    private final Semaphore pauseSem = new Semaphore(1);
    private final PriorityQueue<Command> inputCommand = new PriorityQueue<>();
    @NotEquals
    private final Lambdas<GameEndStatus> endFunctions;

    /**
     * @param gameView  is the {@link GameView} in which the Game Controller operates
     * @param player    is the selected player to play the game
     * @param game      is the game selected to start in this game
     * @param endFunctions            function used when the application is closed.
     * @throws ClassNotFoundException if the class of the entity is not found.
     * @throws IllegalAccessException if the game word you want to launch does not exist.
     * @throws InstantiationException if the game word you want to launch does not exist.
     * @throws IOException            if the game word you want to launch does not exist.
     */
    public GameController(final GameView gameView, final PlayerEnum player, final String game, final Lambdas<GameEndStatus> endFunctions)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
        this.gameWorld = new GameWorldImpl(game, FactoryPlayersUtils.getPlayer(player));
        this.stopped = false;
        this.gameloop = new GameLoop();
        this.entityControllers = new HashMap<UUID, EntityController>();
        this.endFunctions = endFunctions;
        //set game view
        this.gameView = gameView;
        this.gameView.addStatistic(new HeartStatisticView());
        this.gameView.addStatistic(new BombStatisticView());
        this.gameView.addStatistic(new KeyStatisticView());
        gameView.setRoomView(new RoomView());
    }

    /**
     * 
     * @param roomView the room view that you want to set in the current game view.
     */
    public void setRoomView(final RoomView roomView) {
        this.gameView.setRoomView(roomView);
    }
    /**
     * to start the game loop.
     */
    public void start() {
        this.stopped = false;
        this.gameloop.start();
    }

    /**
     * to stop the game loop.
     */
    public void stop() {
        this.stopped = true;
    }
    /**
     * Pause the game.
     */
    public void pause() {
        pauseSem.drainPermits();
    }
    /**
     * Resume the game.
     */
    public void resume() {
        pauseSem.release();
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
            GameEndStatus gameStatus = GameEndStatus.WIN;
            try {
                while (!stopped) {
                    sleep(TIMETOSLEEP);
                    pauseSem.acquire();
                    pauseSem.release();
                    // I check if the player is still alive
                    gameStatus = gameWorld.update(TIMETOSLEEP);
                    if (gameStatus != GameEndStatus.RUNNING) {
                        stopped = true;
                    } else {
                        //proportions between model and view
                        final double widthMolti = gameView.getWidth()
                                / gameWorld.getActiveFloor().getActiveRoom().getWidth();
                        final double heightMolti = gameView.getHeight()
                                / gameWorld.getActiveFloor().getActiveRoom().getHeight();
                        //if we changed room or floor
                        if (gameWorld.isChangeFloor() || gameWorld.getActiveFloor().isChangeRoom()) {
                            final EntityInformation disappear = new EntityInformation().setStatus(BasicStatusEnum.DISAPPEAR);
                            entityControllers.values().stream().forEach(x -> {
                                                                                x.update(disappear);
                                                                            });
                            entityControllers.clear();
                        }
                        //update view
                        gameWorld.getEntityInformation().stream().peek(i -> {
                                                    i.setWidth(i.getWidth() * widthMolti)
                                                     .setHeight(i.getHeight() * heightMolti)
                                                     .setPosition(new Position(
                                                                         i.getPosition().getX(),
                                                                         gameView.getHeight() - i.getPosition().getY() - i.getHeight(),
                                                                         i.getPosition().getZ()));
                        }).peek(st -> {
                            if (!entityControllers.containsKey(st.getId())) {
                                try {
                                    entityControllers.put(st.getId(), new EntityController(st, gameView));
                                } catch (ClassNotFoundException | NoSuchMethodException | SecurityException
                                        | InstantiationException | IllegalAccessException | IllegalArgumentException
                                        | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).forEach(st -> {
                            entityControllers.get(st.getId()).update(st);
                            if (st.getStatus().equals(BasicStatusEnum.DISAPPEAR)
                                    || st.getStatus().equals(BasicStatusEnum.DEAD)) {
                                entityControllers.remove(st.getId());
                            }
                        });
                        //update statistic of player
                        final StatisticsInformations stats = gameWorld.getPlayer().getStatisticsInformations();
                        gameView.setInventoryStatistic(gameView.getStatistics()
                                                        .stream()
                                                        .filter(s -> BombStatisticView.class.isInstance(s))
                                                        .findAny()
                                                        .get(), stats.getBombs())
                        .setInventoryStatistic(gameView.getStatistics()
                                                        .stream()
                                                        .filter(s -> KeyStatisticView.class.isInstance(s))
                                                        .findAny()
                                                        .get(), stats.getKeys())
                        .setHeartsStatistic(gameView.getStatistics()
                                                    .stream()
                                                    .filter(s -> s.getClass().equals(HeartStatisticView.class))
                                                    .map(s -> HeartStatisticView.class.cast(s)).findAny().get(), 
                                                        stats.getHearts());
                        gameView.draw();
                        gameView.updateEntity();
                        gameWorld.getActiveFloor().getActiveRoom().getEntities().forEach(e -> {
                            e.getStatusComponent().setMove(BasicMovementEnum.STATIONARY);
                            e.getStatusComponent().setStatus(BasicStatusEnum.DEFAULT);
                        });
                        if (inputDisponible.tryAcquire()) {
                            gameWorld.input(inputCommand);
                            inputDisponible.release();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            endFunctions.use(gameStatus);
        }
    }

    /**
     * 
     * @param plEnumMenu is the enumeration that matches you have data of a specific player.
     * @return a {@link DataPlayer} that contains all the data of a relative player
     * @throws ClassNotFoundException if there is no player that corresponds to the enumeration entered.
     */
    public static DataPlayer getDataPlayer(final PlayerEnum plEnumMenu) throws ClassNotFoundException {
        return FactoryPlayersUtils.getDataPlayer(plEnumMenu);
    }

    /**
     * Through this method the view sends the commands to the model.
     * @param cm list of commands to send to the model.
     */
    public void input(final Set<Command> cm) {
        if (inputDisponible.tryAcquire()) {
            inputCommand.clear();
            inputCommand.addAll(cm);
            inputDisponible.release();
        }
    }
}
