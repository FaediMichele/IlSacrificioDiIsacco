package view.javafx.menu;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import view.SubMenuView;
import view.javafx.ViewGetterUtil;
import view.node.SelectList;
import view.node.javafx.SelectListJavafx;

/**
 * TODO .
 */
public class SubMenuOptionView implements SubMenuView {
    private static final String NEWRUN = "/menuImgs/newRun.png";
    private static final String OPTION = "/menuImgs/options.png";
    private static final String ARROW = "/menuImgs/selector.png";
    private final SelectList sl = new SelectListJavafx();

    private final Pane main;

    /**
     * TODO .
     */
    public SubMenuOptionView() {
        main = ViewGetterUtil.getNodeByName("pnInGameMenu", Pane.class);
        final ImageView newRun = ViewGetterUtil.getNodeByName("imgContinuePlaying", ImageView.class);
        final ImageView option = ViewGetterUtil.getNodeByName("imgBackToGame", ImageView.class);
        final ImageView imgSelector = ViewGetterUtil.getNodeByName("imgSelectorInGameMenu", ImageView.class);
        sl.addItems(newRun, option);
        sl.setDistance(new Pair<Double, Double>(-imgSelector.getBoundsInParent().getWidth(), imgSelector.getBoundsInParent().getHeight()));
        sl.setSelector(imgSelector);
        imgSelector.boundsInParentProperty().addListener(b -> {
            Platform.runLater(() -> sl.setDistance(new Pair<Double, Double>(-imgSelector.getBoundsInParent().getWidth(), imgSelector.getBoundsInParent().getHeight())));
        });
        newRun.setImage(new Image(NEWRUN));
        option.setImage(new Image(OPTION));
        imgSelector.setImage(new Image(ARROW));
    }
    @Override
    public final Object getMain() {
        return main;
    }

}
