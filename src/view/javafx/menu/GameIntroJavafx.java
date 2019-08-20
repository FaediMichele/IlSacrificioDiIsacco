package view.javafx.menu;

import java.util.Set;

import controller.menu.InputMenu;
import controller.menu.MainMenuSelection;
import controller.menu.MenuSelection;
import controller.menu.SubMenu;
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
public final class GameIntroJavafx extends InputMenu<SubMenu> {
    private static final int DIMENSIONSCREENTESTX = 1920;
    private static final int DIMENSIONSCREENTESTY = 1030;
    private static final int SCALEMULTIPLIER = 3;

    private static final String INTROFILEPATH = "/video/intro.mp4";
    private static final String NAMEPANE = "pnIntro1";
    private final MySubMenu s;
    private final FadeTransition fd;
    private final Pane pnMain;
    private final int defaultX;
    private final int defaultY;

    /**
     * Create the SubMenuSelection for the introduction.
     * @param msMenu the time for the fade effect.
     */
    public GameIntroJavafx(final long msMenu) {
        super();
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
    @Override
    public void input(final Set<Command> commands) {
        super.input(commands);
        getFather().get().select(MainMenuSelection.class);
    }

    private void setBind(final Pane p, final Scene s) {
        p.layoutXProperty().bind(s.widthProperty().divide(2).subtract(p.widthProperty().divide(2)));
        p.layoutYProperty().bind(s.heightProperty().divide(2).subtract(p.heightProperty().divide(2)));
        p.scaleYProperty().bind(p.scaleXProperty());
    }
    private void updateBind(final Pane p, final Scene s) {
        if (pnMain.getWidth() / defaultX > pnMain.getHeight() / defaultY) {
            p.scaleXProperty().bind(s.widthProperty().multiply(SCALEMULTIPLIER).divide(DIMENSIONSCREENTESTX));
        } else {
            p.scaleXProperty().bind(s.heightProperty().multiply(SCALEMULTIPLIER).divide(DIMENSIONSCREENTESTY));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fatherChanged(final MenuSelection<?> previous, final MenuSelection<?> next, final Object param) {
        super.fatherChanged(previous, next, param);
        if (next.equals(this)) {
            s.selectChild();
            fd.setToValue(1);
            fd.playFromStart();
        } else {
            fd.setToValue(0);
            fd.playFromStart();
            fd.setOnFinished(e -> s.release());
        }
    }

    private static final class MySubMenu extends SubMenu {
        private final MediaView mv;
        MySubMenu(final MenuSelection<SubMenu> selector, final MediaView mv, final String videoPath) {
            super(selector);
            this.mv = mv;
            mv.setMediaPlayer(new MediaPlayer(new Media(getClass().getResource(videoPath).toExternalForm())));
            mv.getMediaPlayer().setOnEndOfMedia(this::end);
        }

        @Override
        public void selectChild() {
            mv.getMediaPlayer().play();
        }

        private void end() {
            this.getFather().getFather().get().select(MainMenuSelection.class);
        }

        public void release() {
            mv.getMediaPlayer().stop();
            mv.setMediaPlayer(null); // release the memory
        }

        @Override
        public void disownedChild() {
        }

        @Override
        public SubMenuView getSubMenuView() {
            return null;
        }
    }

}
