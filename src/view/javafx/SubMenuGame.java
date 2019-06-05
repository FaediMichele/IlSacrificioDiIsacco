package view.javafx;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import view.SubMenu;
import view.SubMenuSelection;
import view.node.SelectList;
import view.node.SelectListJavafx;

/**
 * This sub menu is used for the "save" menu. (new run, option, continue, ...).
 */
public class SubMenuGame extends SubMenu {

    private final SelectList sl = new SelectListJavafx();
    private final ImageView newRun;
    private final ImageView option;

    /**
     * Create the save menu.
     * @param pnMain the {@link Pane} that contains the other @param.
     * @param selector the {@link SubMenuSelection}.
     * @param newRun the newRun {@link ImageView}.
     * @param option the option {@link ImageView}.
     * @param imgSelector the arrow {@link ImageView}.
     */
    public SubMenuGame(final SubMenuSelection selector, final Pane pnMain, final ImageView newRun, final ImageView option, final ImageView imgSelector) {
        super(selector, pnMain);
        this.newRun = newRun;
        this.option = option;
        sl.addItems(newRun, option);
        sl.setDistance(new Pair<Double, Double>(-imgSelector.getBoundsInParent().getWidth(), imgSelector.getBoundsInParent().getHeight()));
        sl.setSelector(imgSelector);
        imgSelector.boundsInParentProperty().addListener(b -> {
            sl.setDistance(new Pair<Double, Double>(-imgSelector.getBoundsInParent().getWidth(), imgSelector.getBoundsInParent().getHeight()));
        });
    }

    @Override
    public final void up() {
        sl.previous();
    }

    @Override
    public final void down() {
        sl.next();
    }

    @Override
    public final void enter() {
        if (sl.get().equals(newRun) && getSelector().contains(SubMenuRun.class)) {
            getSelector().select(SubMenuRun.class);
        }
        if (sl.get().equals(option) && getSelector().contains(SubMenuOption.class)) {
            getSelector().select(SubMenuOption.class);
        }
    }

    @Override
    public final void reset() {
        sl.initial();
    }

}
