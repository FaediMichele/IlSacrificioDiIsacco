package controller;

/**
 * The {@link Controller} for the game which includes the game loop and it
 * communicates with the View and checks the Model.
 *
 */
public class GameController extends AbstractController {
    private final Thread gameLoopThread;

    /**
     * 
     * @param main the {@link MainController}
     */
    public GameController(final MainController main) {
        super(main);
        this.gameLoopThread = new Thread() {
            @Override
            public void run() {
                super.run();
                gameLoop();
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        super.run();
        this.gameLoopThread.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        super.stop();
        //TODO
    }

    /**
     * The method that handles the game loop on the thread.
     */
    protected void gameLoop() {

    }
}
