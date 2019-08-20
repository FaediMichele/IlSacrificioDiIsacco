package view.javafx.menu;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import util.Lambda;
import view.interfaces.SubMenuIntroView;
import view.javafx.ViewGetterUtil;

/**
 * Implementation of {@link SubMenuIntroView} for javafx.
 */
public class SubMenuIntroViewImpl implements SubMenuIntroView {
    private static final String INTROFILEPATH = "/video/intro.mp4";
        private final MediaView mv;
        private final Lambda l;
        /**
         * Create the sub menu for the intro.
         * @param l function called when the intro is ended.
         */
        public SubMenuIntroViewImpl(final Lambda l) {
            this.mv = ViewGetterUtil.getNodeByName("mvIntro", MediaView.class);
            mv.setMediaPlayer(new MediaPlayer(new Media(getClass().getResource(INTROFILEPATH).toExternalForm())));
            mv.getMediaPlayer().setOnEndOfMedia(this::clean);
            this.l = l;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void play() {
            mv.getMediaPlayer().play();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clean() {
            mv.getMediaPlayer().stop();
            mv.setMediaPlayer(null); // release the memory
            l.use();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getMain() {
            return ViewGetterUtil.getNodeByName("pnIntro1", Pane.class);
        }
}
