package controller;


import model.game.GameWorld;

/**
 * The {@link Controller} for the game which includes the game loop and it
 * communicates with the View and checks the Model.
 *
 */
public class GameController extends AbstractController {
    private boolean stop;
    private final GameWorld gameWord;
    /**
     * 
     * @param main the {@link MainController}
     * @param gameWorld is game world
     */
    public GameController(final MainController main, final GameWorld gameWorld) {
        super(main);
        this.gameWord = gameWorld;
        this.stop = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        super.run();
        this.stop = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
       super.stop();
       this.stop = true;
    }

//    /**
//     * The method that handles the game loop on the thread.
//     */
//    protected void gameLoop() {
//        while (!this.stop) {
//            //implementare il thread
//           // this.gameWord.update(deltaTime);
//        }
//    }
}
