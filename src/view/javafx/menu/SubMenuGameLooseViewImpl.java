package view.javafx.menu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import view.Sound;
import view.TypeOfAudio;
import view.interfaces.SubMenuGameLooseView;
import view.javafx.SoundJavafx;
import view.javafx.ViewGetterUtil;

/**
 * The implementation for JavaFx for the SubMenuLooseView.
 */
public class SubMenuGameLooseViewImpl implements SubMenuGameLooseView {
    private static final String IMAGEPATH = "/menuImgs/loose.png";
    private final Sound death = new SoundJavafx("/menuSound/isaacDied.wav", TypeOfAudio.EFFECT);

    /**
     * Load the View for the sub menu for the loose screen.
     */
    public SubMenuGameLooseViewImpl() {
        final ImageView loose = ViewGetterUtil.getNodeByName("imgLoose", ImageView.class);
        loose.setImage(new Image(IMAGEPATH));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startAudio() {
        death.play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getMain() {
        return ViewGetterUtil.getNodeByName("pnGameLoose", Pane.class);
    }
}
