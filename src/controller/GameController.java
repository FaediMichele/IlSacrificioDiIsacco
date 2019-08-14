package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import model.entity.FactoryPlayersUtil;
import model.enumeration.BasicMovementEnum;
import model.enumeration.BasicStatusEnum;
import model.enumeration.PlayerEnum;
import model.game.GameWorld;
import model.game.GameWorldImpl;
import model.util.DataPlayer;
import model.util.EntityInformation;
import model.util.Position;
import model.util.StatisticsInformations;
import util.Command;
import util.Lambda;
import util.NotEquals;
import view.javafx.game.GameView;
import view.javafx.game.HeartStatisticView;
import view.javafx.game.KeyStatisticView;
import view.javafx.game.BombStatisticView;
import view.javafx.game.RoomView;

/**
 * The {@link Controller} for the game which includes the game loop and it
 * communicates with the View and checks the Model.
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
    private final PriorityQueue<Command> inputCommand = new PriorityQueue<>();
    @NotEquals
    private final Lambda l;

    /**
     * @param gameView is the {@link GameView} in which the Game Controller operates
     * @param player   .
     * @param game     .
     * @param l lambda when the application ends.
     * @throws ClassNotFoundException .
     * @throws IllegalAccessException .
     * @throws InstantiationException .
     * @throws IOException .
     */
    public GameController(final GameView gameView, final PlayerEnum player, final String game, final Lambda l)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
        this.gameView = gameView;
        this.gameWorld = new GameWorldImpl(game, FactoryPlayersUtil.getPlayer(player));
        this.stopped = false;
        this.gameloop = new GameLoop();
        this.entityControllers = new HashMap<UUID, EntityController>();
        this.l = l;
        this.initStatistics();
        gameView.setRoomView(new RoomView("/gameImgs/basement_background1_640x344.png"));
    }

    private void initStatistics() {
        gameView.addStatistic(new HeartStatisticView());
        gameView.addStatistic(new BombStatisticView());
        gameView.addStatistic(new KeyStatisticView());
    }

    /**
     * {@inheritDoc}
     */
    public void start() {
        // super.run();
        this.stopped = false;
        this.gameloop.start();
    }

    /**
     * {@inheritDoc}
     */
    public void stop() {
        // super.stop();
        this.stopped = true;
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
                while (!stopped) {
                    sleep(TIMETOSLEEP);
                    if (!gameWorld.update(TIMETOSLEEP)) {
                        stopped = true;
                    }
                    // Se il Player è morto -> gameView.gameOver()
                    final double widthMolti = gameView.getWidth()
                            / gameWorld.getActiveFloor().getActiveRoom().getWidth();
                    final double heightMolti = gameView.getHeight()
                            / gameWorld.getActiveFloor().getActiveRoom().getHeight();
                    if (gameWorld.isChangeFloor() || gameWorld.getActiveFloor().isChangeRoom()) {
                        System.out.println(gameWorld.getActiveFloor().getRooms().indexOf(gameWorld.getActiveFloor().getActiveRoom()));
                        final EntityInformation disappear = new EntityInformation()
                                .setStatus(BasicStatusEnum.DISAPPEAR);
                        entityControllers.values().stream().forEach(x -> {
                            x.update(disappear);
                        });
                        entityControllers.clear();
                    }
                    gameWorld.getEntityInformation().stream().peek(i -> {
                        i.setWidth(i.getWidth() * widthMolti).setHeight(i.getHeight() * heightMolti)
                                .setPosition(new Position(i.getPosition().getX(), gameView.getHeight() - i.getPosition().getY() - i.getHeight(), i.getPosition().getZ()));

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
                    final StatisticsInformations stats = gameWorld.getPlayer().getStatisticsInformations();
                    gameView.setInventoryStatistic(gameView.getStatistics().stream()
                            .filter(s -> s instanceof BombStatisticView).findAny().get(), stats.getBombs());
                    gameView.setInventoryStatistic(gameView.getStatistics().stream()
                            .filter(s -> s instanceof KeyStatisticView).findAny().get(), stats.getKeys());
                    gameView.setHeartsStatistic(
                            gameView.getStatistics().stream().filter(s -> s.getClass().equals(HeartStatisticView.class))
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             l.use();
        }
    }

    /**
     * 
     * @param plEnumMenu .
     * @return .
     * @throws ClassNotFoundException .
     */
    public static DataPlayer getDataPlayer(final PlayerEnum plEnumMenu) throws ClassNotFoundException {
        return FactoryPlayersUtil.getDataPlayer(plEnumMenu);
    }

    /**
     * 
     * @param cm .
     */
    public void input(final Set<Command> cm) {
        if (inputDisponible.tryAcquire()) {
            inputCommand.clear();
            inputCommand.addAll(cm);
            inputDisponible.release();
        }
    }
}
