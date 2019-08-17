package view.javafx.menu;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import view.Sound;
import view.TypeOfAudio;
import view.interfaces.SubMenuOptionView;
import view.javafx.SoundJavafx;
import view.javafx.ViewGetterUtil;
import view.node.SelectList;
import view.node.TwoStateNode;
import view.node.javafx.SelectListJavafx;
import view.node.javafx.TwoStateNodeJavafx;

/**
 * Implementation for the sub menu option in the main menu.
 */
public class SubMenuOptionViewImpl implements SubMenuOptionView {
    private static final double VOLUMEINCREASE = 0.1;
    private static final String PANE = "pnOption";
    private static final String BACKGROUND = "/menuImgs/optionsMenuBackground.png";
    private static final String ARROW = "/menuImgs/selectorInverse.png";
    private static final String ON = "/menuImgs/on.png";
    private static final String OFF = "/menuImgs/off.png";
    private final SelectList sl = new SelectListJavafx();
    private final TwoStateNode fullScreenNode;
    private final ImageView fullScreen;
    private final ProgressBar effect;
    private final ProgressBar music;
    private final Sound volumeSetter = new SoundJavafx();

    /**
     * Create a sub menu for the option.
     * @param args
     */
    public SubMenuOptionViewImpl() {
        ViewGetterUtil.getNodeByName("imgOptionBackGround", ImageView.class).setImage(new Image(BACKGROUND));
        fullScreen = ViewGetterUtil.getNodeByName("imgFullScreen", ImageView.class);
        effect = ViewGetterUtil.getNodeByName("prgVolumeEffect", ProgressBar.class);
        music = ViewGetterUtil.getNodeByName("prgVolumeMusic", ProgressBar.class);
        final ImageView imgSelector = ViewGetterUtil.getNodeByName("imgSelectorOption", ImageView.class);
        final Pair<Double, Double> pair = new Pair<Double, Double>(-imgSelector.getBoundsInParent().getWidth() * 3, -imgSelector.getBoundsInParent().getHeight());
        sl.addItems(fullScreen, effect, music);
        sl.setDistance(pair);
        sl.setSelector(imgSelector);
        imgSelector.boundsInParentProperty().addListener(b -> {
            Platform.runLater(() -> sl.setDistance(pair));
        });
        fullScreenNode = new TwoStateNodeJavafx(fullScreen, new Image(ON), new Image(OFF), ViewGetterUtil.isFullScreen());
        ViewGetterUtil.addListenerFullScreen(() -> fullScreenNode.change());
        imgSelector.setImage(new Image(ARROW));
        effect.setProgress(volumeSetter.getVolume(TypeOfAudio.EFFECT));
        music.setProgress(volumeSetter.getVolume(TypeOfAudio.MUSIC));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void up() {
        sl.previous();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void down() {
        sl.next();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void left() {
        if (sl.get().equals(fullScreen)) {
            ViewGetterUtil.switchFullScreen();
        } else if (sl.get().equals(effect) && volumeSetter.getVolume(TypeOfAudio.EFFECT) > 0) {
                volumeSetter.setVolume(volumeSetter.getVolume(TypeOfAudio.EFFECT) - VOLUMEINCREASE, TypeOfAudio.EFFECT);
                effect.setProgress(volumeSetter.getVolume(TypeOfAudio.EFFECT));
        } else if (sl.get().equals(music) && volumeSetter.getVolume(TypeOfAudio.MUSIC) > 0) {
            volumeSetter.setVolume(volumeSetter.getVolume(TypeOfAudio.MUSIC) - VOLUMEINCREASE, TypeOfAudio.MUSIC);
            music.setProgress(volumeSetter.getVolume(TypeOfAudio.MUSIC));
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void right() {
        if (sl.get().equals(fullScreen)) {
            ViewGetterUtil.switchFullScreen();
        } else if (sl.get().equals(effect) && volumeSetter.getVolume(TypeOfAudio.EFFECT) < 1) {
                volumeSetter.setVolume(volumeSetter.getVolume(TypeOfAudio.EFFECT) + VOLUMEINCREASE, TypeOfAudio.EFFECT);
                effect.setProgress(volumeSetter.getVolume(TypeOfAudio.EFFECT));
        } else if (sl.get().equals(music) && volumeSetter.getVolume(TypeOfAudio.MUSIC) < 1) {
            volumeSetter.setVolume(volumeSetter.getVolume(TypeOfAudio.MUSIC) + VOLUMEINCREASE, TypeOfAudio.MUSIC);
            music.setProgress(volumeSetter.getVolume(TypeOfAudio.MUSIC));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getMain() {
        return ViewGetterUtil.getNodeByName(PANE, Pane.class);
    }

}
