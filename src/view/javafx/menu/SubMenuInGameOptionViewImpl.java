package view.javafx.menu;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import view.interfaces.SubMenuInGameOptionView;
import view.javafx.ViewGetterUtil;
import view.node.SelectList;
import view.node.javafx.SelectListJavafx;

/**
 * Implementation of the {@link SubMenuInGameOptionView}.
 */
public class SubMenuInGameOptionViewImpl implements SubMenuInGameOptionView {
    private static final String NEWRUN = "/menuImgs/newRun.png";
    private static final String OPTION = "/menuImgs/options.png";
    private static final String ARROW = "/menuImgs/selector.png";
    private static final String BACKGROUND = "/menuImgs/popUpBackground.png";
    private final Map<ImageView, Object> operation = new LinkedHashMap<>();
    private final SelectList sl = new SelectListJavafx();

    private final Pane main;

    /**
     * Create a new {@link SubMenuInGameOptionViewImpl} with parameter returned when uses select.
     * @param args Object returned when is called select.
     */
    public SubMenuInGameOptionViewImpl(final Object... args) {
        main = ViewGetterUtil.getNodeByName("pnInGameMenu", Pane.class);
        final ImageView newRun = ViewGetterUtil.getNodeByName("imgContinuePlaying", ImageView.class);
        final ImageView option = ViewGetterUtil.getNodeByName("imgBackToGame", ImageView.class);
        final ImageView imgSelector = ViewGetterUtil.getNodeByName("imgSelectorInGameMenu", ImageView.class);
        final ImageView imgBackground = ViewGetterUtil.getNodeByName("imgPopUpBackground", ImageView.class);
        operation.put(newRun, args[0]);
        operation.put(option, args[1]);
        sl.addItems(newRun, option);
        sl.setDistance(new Pair<Double, Double>(-imgSelector.getBoundsInParent().getWidth(), imgSelector.getBoundsInParent().getHeight() / 2));
        sl.setSelector(imgSelector);
        imgSelector.boundsInParentProperty().addListener(b -> {
            Platform.runLater(() -> sl.setDistance(new Pair<Double, Double>(-imgSelector.getBoundsInParent().getWidth(), imgSelector.getBoundsInParent().getHeight() / 2)));
        });
        newRun.setImage(new Image(NEWRUN));
        option.setImage(new Image(OPTION));
        imgSelector.setImage(new Image(ARROW));
        imgBackground.setImage(new Image(BACKGROUND));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void down() {
        sl.next();
    }
    /**
     * {@inheritDoc}.
     */
    @Override
    public void up() {
        sl.previous();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Object select() {
        return operation.get(sl.get());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void reset() {
        sl.initial();
    }
    @Override
    public final Object getMain() {
        return main;
    }

}
