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
import model.enumeration.BasicEntityEnum;
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
    private static final double PADDING_X_MAP = 69;
    private static final double PADDING_Y_MAP = 90;
    private static final long TIMETOSLEEP = 50;

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
     * @param player   .
     * @param game     .
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws IOException 
     */
    public GameController(final GameView gameView, final PlayerEnum player, final String game)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
        this.gameView = gameView;
        this.gameWord = new GameWorldImpl(game, FactoryPlayersUtil.getPlayer(player));
        this.stoped = false;
        this.gameloop = new GameLoop();
        this.entityControllers = new HashMap<UUID, EntityController>();
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
                    sleep(TIMETOSLEEP);
                    if (!gameWord.update(TIMETOSLEEP)) {
                        System.out.println("Game should over"); // TODO
                    } 
                    gameWord.update(TIMETOSLEEP);
                    // Se il Player è morto -> gameView.gameOver()
                    final double widthMolti = gameView.getWidth()
                            / gameWord.getActiveFloor().getActiveRoom().getWidth();
                    final double heightMolti = gameView.getHeight()
                            / gameWord.getActiveFloor().getActiveRoom().getHeight();
                    final double heightAdd = gameView.getHeight() - PADDING_Y_MAP;
                    if (gameWord.isChangeFloor() || gameWord.getActiveFloor().isChangeRoom()) {
                        final EntityInformation disappear = new EntityInformation()
                                .setStatus(BasicStatusEnum.DISAPPEAR);
                        entityControllers.values().stream().forEach(x -> {
                            x.update(disappear);
                        });
                        entityControllers.clear();
                    }
                    gameWord.getEntityInformation().stream().peek(i -> {
                        if (i.getEntityName() == BasicEntityEnum.DOOR) {
                            if (i.getMove() == BasicMovementEnum.UP) {
                                final double minusY = 10;
                                final double addX = 0;
                                i.setPosition(new Position(PADDING_X_MAP + i.getPosition().getX() + addX, heightAdd - i.getPosition().getY() - minusY, i.getPosition().getZ()));
                            } else if (i.getMove() == BasicMovementEnum.RIGHT) {
                                final double minusY = 20;
                                final double addX = 5;
                                i.setPosition(new Position(PADDING_X_MAP + i.getPosition().getX() + addX, heightAdd - i.getPosition().getY() - minusY, i.getPosition().getZ()));
                            } else if (i.getMove() == BasicMovementEnum.DOWN) {
                                final double addY = 25;
                                final double addX = 0;
                                i.setPosition(new Position(PADDING_X_MAP + i.getPosition().getX() + addX, heightAdd - i.getPosition().getY() + addY, i.getPosition().getZ()));
                            } else if (i.getMove() == BasicMovementEnum.LEFT) {
                                final double minusX = 30;
                                final double minusY = 20;
                                i.setPosition(new Position(PADDING_X_MAP + i.getPosition().getX() - minusX, heightAdd - i.getPosition().getY() - minusY, i.getPosition().getZ()));
                            } else {
                                i.setPosition(new Position(PADDING_X_MAP + i.getPosition().getX(), heightAdd - i.getPosition().getY(), i.getPosition().getZ()));
                            }
                            final double add = 20;
                            i.setWidth((i.getWidth() + add) * widthMolti).setHeight((i.getHeight() + add) * heightMolti);
                        } else {
                            i.setWidth(i.getWidth() * widthMolti).setHeight(i.getHeight() * heightMolti)
                                    .setPosition(new Position(PADDING_X_MAP + i.getPosition().getX(),
                                            heightAdd - i.getPosition().getY(), i.getPosition().getZ()));
                        }

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
                    final StatisticsInformations stats = gameWord.getPlayer().getStatisticsInformations();
                    gameView.setInventoryStatistic(gameView.getStatistics().stream()
                            .filter(s -> s instanceof BombStatisticView).findAny().get(),
                            stats.getBombs());
                    gameView.setInventoryStatistic(gameView.getStatistics().stream()
                            .filter(s -> s instanceof KeyStatisticView).findAny().get(), stats.getKeys());
                    gameView.setHeartsStatistic(
                            gameView.getStatistics().stream().filter(s -> s.getClass().equals(HeartStatisticView.class))
                                    .map(s -> HeartStatisticView.class.cast(s)).findAny().get(),
                            stats.getHearts());
                    gameView.draw();
                    gameView.updateEntity();
                    gameWord.getActiveFloor().getActiveRoom().getEntities().forEach(e -> {
                        e.getStatusComponent().setMove(BasicMovementEnum.STATIONARY);
                        e.getStatusComponent().setStatus(BasicStatusEnum.DEFAULT);
                    });
                    if (inputDisponible.tryAcquire()) {
                        gameWord.input(inputCommand);
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
    public void input(final Set<Command> cm) {
        if (inputDisponible.tryAcquire()) {
            inputCommand.clear();
            inputCommand.addAll(cm);
            inputDisponible.release();
        }
    }
}
