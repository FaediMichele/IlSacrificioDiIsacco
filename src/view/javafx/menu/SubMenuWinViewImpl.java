package view.javafx.menu;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import util.Lambda;
import view.interfaces.SubMenuWinView;
import view.javafx.ViewGetterUtil;

/**
 * Implementation of SubMenuWinView.
 */
public class SubMenuWinViewImpl implements SubMenuWinView {
    private static final String PANE = "pnGameWin";
    private static final String FILM = "mvWin";
    private static final String PATH = "/video/end.mp4";
    private final MediaView mv;

    /**
     * Initialize.
     * @param l lambda called when the video end.
     */
    public SubMenuWinViewImpl(final Lambda l) {
        mv = ViewGetterUtil.getNodeByName(FILM, MediaView.class);
        mv.setMediaPlayer(new MediaPlayer(new Media(getClass().getResource(PATH).toExternalForm())));
        mv.getMediaPlayer().stop();
        mv.getMediaPlayer().setOnEndOfMedia(() -> {
            mv.getMediaPlayer().stop();
            l.use();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        mv.getMediaPlayer().play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        mv.getMediaPlayer().stop();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Object getMain() {
        return ViewGetterUtil.getNodeByName(PANE, Pane.class);
    }
}
