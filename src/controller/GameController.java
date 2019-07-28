package controller;


import java.util.ArrayList;
import java.util.List;

import model.game.GameWorld;

/**
 * The {@link Controller} for the game which includes the game loop and it
 * communicates with the View and checks the Model.
 *
 */
public class GameController extends AbstractController {
    private static  long timeToSleep = 33;
    private boolean stop;
    private final GameWorld gameWord;
    private final GameLoop gameloop;
    private final List<EntityController> entityControllerList;
    /**
     * 
     * @param main the {@link MainController}
     * @param gameWorld is game world
     */
    public GameController(final MainController main, final GameWorld gameWorld) {
        super(main);
        this.gameWord = gameWorld;
        this.stop = false;
        this.gameloop = new GameLoop();
        this.entityControllerList = new ArrayList<EntityController>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        super.run();
        this.stop = false;
        this.gameloop.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
       super.stop();
       this.stop = true;
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
                while (!stop) {
                    sleep(timeToSleep);
                    gameWord.update(timeToSleep);
                    //guardo se è cambiato il piano o la stanza
//                    gameWord.getActiveFloor().getActiveRoom().getEntitysStatus()
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
