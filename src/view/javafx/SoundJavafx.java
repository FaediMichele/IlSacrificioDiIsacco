package view.javafx;

import javafx.scene.media.AudioClip;
import view.Sound;

/**
 * Implementation of {@link Sound} for javafx.
 *
 */
public class SoundJavafx implements Sound {
    private AudioClip a;

    /**
     * Create a new sound based on a file.
     * @param path the path of the file.
     */
    public SoundJavafx(final String path) {
        init(path);
    }
    private void init(final String path) {
        if (a != null) {
            throw new IllegalStateException("Already inited");
        }
        a = new AudioClip(getClass().getResource(path).toExternalForm());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play() {
        a.stop();
        a.play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        a.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playInLoop() {
        a.setCycleCount(AudioClip.INDEFINITE);
        a.play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaying() {
        return a.isPlaying();
    }

}
