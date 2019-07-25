package controller;

/**
 * The {@link Controller} for the game which includes the game loop and it
 * communicates with the View and checks the Model.
 *
 */
public class GameController extends AbstractController {
 //   private boolean stop;
    /**
     * 
     * @param main the {@link MainController}
     */
    public GameController(final MainController main) {
        super(main);
        //sthis.stop = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        super.run();
   //     this.stop = false;
        this.gameLoop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        super.stop();
       // this.stop = true;
    }

    /**
     * The method that handles the game loop on the thread.
     */
    protected void gameLoop() {
//        while (!this.stop){
//
//        }
    }
}
