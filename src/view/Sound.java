package view;

/**
 * This interface is for the audio.
 * Load a file and permit to play it once or in loop.
 */
public interface Sound {

    /**
     * Play the sound once.
     */
    void play();
    /**
     * Stop the sound.
     */
    void stop();
    /**
     * Play the sound in loop with a latency between each loop.
     */
    void playInLoop();
}
