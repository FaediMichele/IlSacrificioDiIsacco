package view.javafx;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import util.Lambda;
import util.Single;
import view.Sound;
import view.TypeOfAudio;

/**
 * Implementation of {@link Sound} for javafx.
 *
 */
public class SoundJavafx implements Sound {
    private static final VolumeManager VOLUME = new VolumeManager();
    private static final Double DEFAULTVOLUME = 1.0;
    private MediaPlayer a;
    private boolean playing;

    /**
     * Create a new sound based on a file.
     * @param path the path of the file.
     * @param type the type of audio.
     */
    public SoundJavafx(final String path, final TypeOfAudio type) {
        if (path != null && !path.equals("")) {
            a = new MediaPlayer(new Media(getClass().getResource(path).toExternalForm()));
            VOLUME.addListener(type, () -> a.setVolume(VOLUME.getVolume(type)));
            VOLUME.verifyFirst(type);
        }
        playing = false;
    }

    /**
     * Create an empty SoundJavafx that can be used to change the other sounds.
     */
    public SoundJavafx() {
        playing = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play() {
        verifyPlayer();
        a.stop();
        a.play();
        playing = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        verifyPlayer();
        a.stop();
        playing = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playInLoop() {
        verifyPlayer();
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
        verifyPlayer();
        a.setOnEndOfMedia(() -> l.use());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        if (a != null) {
            a.dispose();
            a = null;
        }
    }

    /**
     * {@inheritDoc}
     * Set the volume of all type of sound in the game.
     */
    @Override
    public void setVolume(final double volume, final TypeOfAudio type) {
        VOLUME.setVolume(volume, type);
    }

    /**
     * {@inheritDoc}
     * Get the volume of all type of sound in the game.
     */
    @Override
    public Double getVolume(final TypeOfAudio type) {
        return VOLUME.getVolume(type);
    }
    private void verifyPlayer() {
        if (a == null) {
            throw new IllegalStateException("Sound disposed or not built due null path");
        }
    }

    private static class VolumeManager {
        private final Map<TypeOfAudio, Single<Double>> volume = new LinkedHashMap<>();
        private final Map<TypeOfAudio, List<Lambda>> onVolumeChanged = new LinkedHashMap<>();
        public void setVolume(final Double newVolume, final TypeOfAudio type) {
            if (!newVolume.equals(volume.get(type).get())) {
                if (volume.get(type) == null) {
                    volume.put(type, new Single<Double>(newVolume));
                } else {
                    volume.get(type).set(newVolume);
                }
                onVolumeChanged.get(type).forEach(Lambda::use);
            }
        }
        public Double getVolume(final TypeOfAudio type) {
            return volume.get(type).get();
        }
        public void addListener(final TypeOfAudio type, final Lambda l) {
            if (onVolumeChanged.get(type) == null) {
                final List<Lambda> lst = new ArrayList<>();
                onVolumeChanged.put(type, lst);
                lst.add(l);
            } else {
                onVolumeChanged.get(type).add(l);
            }
        }
        public void verifyFirst(final TypeOfAudio type) {
            if (!volume.containsKey(type)) {
                volume.put(type, new Single<Double>(DEFAULTVOLUME));
            }
        }
    }
}
