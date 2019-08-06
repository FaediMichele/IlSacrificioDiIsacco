package view.javafx.game.menu;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import view.SubMenuView;
import view.node.SelectList;
import view.node.javafx.SelectListJavafx;

/**
 * The view of the sub menu where select new run or options.
 */
public class SubMenuGameMenuView implements SubMenuView {
    private static final String NEWRUN = "/menuImgs/newRun.png";
    private static final String OPTION = "/menuImgs/options.png";
    private static final String ARROW = "/menuImgs/selector.png";
    private final SelectList sl = new SelectListJavafx();
    private final Map<ImageView, Object> mapSelected;

    /**
     * Create a new {@link SubMenuGameMenuView}.
     * @param args argument to returned by the {@link SubMenuGameMenuView#get()} in order by the selected item.
     */
    public SubMenuGameMenuView(final Object... args) {
        if (args.length < sl.lenght()) {
            throw new IllegalArgumentException("args must be grater or equal to the list of the item(" + sl.lenght() + ")");
        }
        mapSelected = new LinkedHashMap<>();
        final ImageView newRun = ViewGetter.getNodeByName("imgNewRun", ImageView.class);
        final ImageView option = ViewGetter.getNodeByName("imgOptions", ImageView.class);
        sl.addItems(newRun, option);
        final ImageView imgSelector = ViewGetter.getNodeByName("imgSelector", ImageView.class);
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
     * Next item.
     */
    public void next() {
        sl.next();
    }

    /**
     * Previous item.
     */
    public void previous() {
        sl.previous();
    }

    /**
     * Get the valued passed in the {@link SubMenuGameMenuView#SubMenuGameMenuView(Object...)}.
     * @return an object
     */
    public Object get() {
        return mapSelected.get(sl.get());
    }

    /**
     * Go to the initial position.
     */
    public void initial() {
        sl.initial();
    }

    @Override
    public final Object getMain() {
        return ViewGetter.getNodeByName("pnGame", Pane.class);
    }

}
