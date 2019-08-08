package view;

import util.Lambda;

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
     * @param type the type of audio to get. See also {@link TypeOfAudio}.
     */
    void setVolume(double volume, TypeOfAudio type);

    /**
     * Get the volume.
     * @param type the type of audio to get. See also {@link TypeOfAudio}.
     * @return the volume.
     */
    Double getVolume(TypeOfAudio type);
}
