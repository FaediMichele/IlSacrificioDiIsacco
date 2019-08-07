package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import controller.menu.SubMenuGame;
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
import view.javafx.game.RoomView;

/**
 * The {@link Controller} for the game which includes the game loop and it
 * communicates with the View and checks the Model.
 *
 */
public class GameController {
    private static  long timeToSleep = 33;
    @NotEquals
    private volatile boolean stoped;
    private final GameWorld gameWord;
    @NotEquals
    private final GameLoop gameloop;
    private final SubMenuGame gameMenu;
    private final Map<UUID, EntityController> entityControllers;
    /**
     * @param gameMenu is the gameMenu in which the Game Controller operates
     * @param player .
     * @param game .
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws IOException 
     */
    public GameController(final SubMenuGame gameMenu, final PlayerEnum player, final String game) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
        //super(main);
        System.out.println("perchè non esiste printf in java devo ancora capirlo\nGiocatore Selezionato: " + player.getValue());
        this.gameMenu = gameMenu;
        this.gameWord = new GameWorldImpl(game, FactoryPlayersUtil.getPlayer(player));
        this.stoped = false;
        this.gameloop = new GameLoop();
        this.entityControllers = new HashMap<UUID, EntityController>();
        gameMenu.getGameView().setRoomView(new RoomView("/gameImgs/basement_background1.png", null));
        gameMenu.getGameView().draw();
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
                    //Se il Player è morto -> gameMenu.gameOver()
                    final double widthMolti = gameMenu.getGameView().getWidth() 
                                               / gameWord.getActiveFloor().getActiveRoom().getWidth();
                    final double heightMolti = gameMenu.getGameView().getHeight() 
                                               / gameWord.getActiveFloor().getActiveRoom().getHeight();
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
                            .peek(i -> 
                                i.setWidth(i.getWidth() * widthMolti)
                                 .setHeight(i.getHeight() * heightMolti)
                                 .setPosition(new Position(i.getPosition().getX() * widthMolti, 
                                                           i.getPosition().getY() * heightMolti, 
                                                           i.getPosition().getZ()))
                                 )
                            .peek(st -> {
                                    if (!entityControllers.containsKey(st.getId())) {
                                        try {
                                            entityControllers.put(st.getId(), new EntityController(st, gameMenu.getGameView()));
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
    public static DataPlayer getDataPlayer(final PlayerEnum plEnumMenu) throws ClassNotFoundException {
        return FactoryPlayersUtil.getDataPlayer(plEnumMenu);
    }

    /**
     * 
     * @param cm .
     */
    public void input(final Command cm) {
        this.gameWord.input(cm);
    }
}
