package view.javafx.menu;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import view.interfaces.SubMenuSelectMenuView;
import view.javafx.ViewGetterUtil;
import view.node.SelectList;
import view.node.javafx.SelectListJavafx;

/**
 * The implementation view of the sub menu where select new run or options.
 */
public class SubMenuSelectMenuViewImpl implements SubMenuSelectMenuView {
    private static final String NEWRUN = "/menuImgs/newRun.png";
    private static final String OPTION = "/menuImgs/options.png";
    private static final String ARROW = "/menuImgs/selector.png";
    private final SelectList sl = new SelectListJavafx();
    private final Map<ImageView, Object> mapSelected;

    /**
     * Create a new {@link SubMenuSelectMenuViewImpl}.
     * @param args argument to returned by the {@link SubMenuSelectMenuViewImpl#get()} in order by the selected item.
     */
    public SubMenuSelectMenuViewImpl(final Object... args) {
        if (args.length < sl.lenght()) {
            throw new IllegalArgumentException("args must be grater or equal to the list of the item(" + sl.lenght() + ")");
        }
        mapSelected = new LinkedHashMap<>();
        final ImageView newRun = ViewGetterUtil.getNodeByName("imgNewRun", ImageView.class);
        final ImageView option = ViewGetterUtil.getNodeByName("imgOptions", ImageView.class);
        sl.addItems(newRun, option);
        final ImageView imgSelector = ViewGetterUtil.getNodeByName("imgSelector", ImageView.class);
        mapSelected.put(newRun, args[0]);
        mapSelected.put(option, args[1]);
        sl.setDistance(new Pair<Double, Double>(-imgSelector.getBoundsInParent().getWidth(), imgSelector.getBoundsInParent().getHeight()));
        sl.setSelector(imgSelector);
        imgSelector.boundsInParentProperty().addListener(b -> {
            Platform.runLater(() -> sl.setDistance(new Pair<Double, Double>(-imgSelector.getBoundsInParent().getWidth(), imgSelector.getBoundsInParent().getHeight())));
        });
        newRun.setImage(new Image(NEWRUN));
        option.setImage(new Image(OPTION));
        imgSelector.setImage(new Image(ARROW));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void next() {
        sl.next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void previous() {
        sl.previous();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get() {
        return mapSelected.get(sl.get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initial() {
        sl.initial();
    }

    @Override
    public final Object getMain() {
        return ViewGetterUtil.getNodeByName("pnMenuMain", Pane.class);
    }

}
