    package view;

import java.util.ArrayList;
import java.util.List;

import util.Lambda;
import util.Single;

/**
 * This interface is for the audio.
 * Load a file and permit to play it once or in loop.
 */
public interface Sound {
    /**
     * The volume for all the sounds.
     */
    Single<Double> VOLUME = new Single<Double>(Double.valueOf(1.0));

    /**
     * The listener for the volume changed events.
     */
    List<Lambda> VOLUMECHANGED = new ArrayList<>();

    /**s
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

    /**
     * True if the audio is playing. 
     * @return the status of the audio.
     */
    boolean isPlaying();

    /**
     * Set a listener to be called when the audio end.
     * @param l {@link Lambda}.
     */
    void setEndListener(Lambda l);


    /**
     * Release the resources.
     */
    void dispose();

    /**
     * Set the volume of the sounds.
     * @param volume the volume to set.
     */
    default void setVolume(double volume) {
        VOLUME.set(volume);
        VOLUMECHANGED.forEach(Lambda::use);
    }

    /**
     * Get the volume.
     * @return the volume.
     */
    default Double getVolume() {
        return VOLUME.get();
    }
}
