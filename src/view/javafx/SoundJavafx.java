package view.javafx;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import util.Lambda;
import view.Sound;

/**
 * Implementation of {@link Sound} for javafx.
 *
 */
public class SoundJavafx implements Sound {
    private MediaPlayer a;
    private boolean playing;

    /**
     * Create a new sound based on a file.
     * @param path the path of the file.
     */
    public SoundJavafx(final String path) {
        init(path);
        playing = false;
    }
    private void init(final String path) {
        if (a != null) {
            throw new IllegalStateException("Already inited");
        }
        a = new MediaPlayer(new Media(getClass().getResource(path).toExternalForm()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play() {
        a.stop();
        a.play();
        playing = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        a.stop();
        playing = false;
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
        return playing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEndListener(final Lambda l) {
        a.setOnEndOfMedia(() -> l.use());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        a.dispose();
    }

}
