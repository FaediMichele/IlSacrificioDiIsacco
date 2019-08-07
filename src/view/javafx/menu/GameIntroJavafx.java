package view.javafx.menu;

import controller.menu.MainMenuSelection;
import controller.menu.MenuSelection;
import controller.menu.SubMenu;
import controller.menu.SubMenuSelection;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import util.Command;
import view.SubMenuView;
import view.javafx.ViewGetterUtil;

/**
 * The menu for the introduction.
 */
public final class GameIntroJavafx extends SubMenuSelection {
    private static final String INTROFILEPATH = "/video/intro.mp4";
    private static final String NAMEPANE = "pnIntro1";
    private final MySubMenu s;
    private final FadeTransition fd;
    private final Pane pnMain;
    private final int defaultX;
    private final int defaultY;

    /**
     * Create the SubMenuSelection for the introduction.
     * @param parent the {@link MenuSelection}.
     * @param msMenu the time for the fade effect.
     */
    public GameIntroJavafx(final MenuSelection parent, final long msMenu) {
        super(parent);
        final Scene scene = ViewGetterUtil.getScene();
        s = new MySubMenu(this, ViewGetterUtil.getNodeByName("mvIntro", MediaView.class),
                INTROFILEPATH);
        add(s);
        this.pnMain = ViewGetterUtil.getNodeByName("pnIntro", Pane.class);
        this.defaultX = (int) scene.getWidth();
        this.defaultY = (int) scene.getHeight();
        fd = new FadeTransition(Duration.millis(msMenu), pnMain);
        setBind(ViewGetterUtil.getNodeByName(NAMEPANE, Pane.class), scene);
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateBind(ViewGetterUtil.getNodeByName(NAMEPANE, Pane.class), scene);
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            updateBind(ViewGetterUtil.getNodeByName(NAMEPANE, Pane.class), scene);
        });
    }

    private void setBind(final Pane p, final Scene s) {
        p.layoutXProperty().bind(s.widthProperty().divide(2).subtract(p.widthProperty().divide(2)));
        p.layoutYProperty().bind(s.heightProperty().divide(2).subtract(p.heightProperty().divide(2)));
        p.scaleYProperty().bind(p.scaleXProperty());
    }
    private void updateBind(final Pane p, final Scene s) {
        if (pnMain.getWidth() / defaultX < pnMain.getHeight() / defaultY) {
            p.scaleXProperty().bind(s.heightProperty().divide(defaultY));
        } else {
            p.scaleXProperty().bind(s.widthProperty().divide(defaultX));
        }
    }

    @Override
    public  long getTimeAnimation() {
        return (long) fd.getDuration().toMillis();
    }

    @Override
    public  void selectSubMenu(final SubMenu start, final SubMenu end) {
        if (!start.equals(s) || !end.equals(s)) {
            throw new IllegalArgumentException("there is only one sub menu");
        }
    }

    @Override
    public void jumpTo(final SubMenu dest) {
        if (!dest.equals(s)) {
            throw new IllegalArgumentException("there is only one sub menu");
        }
    }

    @Override
    public void selectMenu(final SubMenuSelection previous, final SubMenuSelection dest) {
        if (previous.equals(this)) {
            fd.setToValue(0);
            fd.playFromStart();
            fd.setOnFinished(e -> s.release());
        } else {
            s.select();
            fd.setToValue(1);
            fd.playFromStart();
        }
    }

    private static final class MySubMenu extends SubMenu {
        private final MediaView mv;
        MySubMenu(final SubMenuSelection selector, final MediaView mv, final String videoPath) {
            super(selector);
            this.mv = mv;
            mv.setMediaPlayer(new MediaPlayer(new Media(getClass().getResource(videoPath).toExternalForm())));
            mv.getMediaPlayer().setOnEndOfMedia(this::end);
        }

        @Override
        public void input(final Command c) {
            end();
        }

        @Override
        public void select() {
            mv.getMediaPlayer().play();
        }

        private void end() {
            this.getSelector().getParent().select(MainMenuSelection.class);
        }

        public void release() {
            mv.getMediaPlayer().stop();
            mv.setMediaPlayer(null); // release the memory
        }

        @Override
        public void reset() {
        }

        @Override
        public SubMenuView getSubMenuView() {
            return null;
        }
    }

}
