package view.javafx;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import view.Command;
import view.SubMenu;
import view.SubMenuSelection;
import view.node.SelectList;

/**
 * This sub menu is used for the "save" menu. (new run, option, continue, ...).
 */
public class SubMenuGame extends SubMenu {

    private static final String NEWRUN = "/menuImgs/newRun.png";
    private static final String OPTION = "/menuImgs/options.png";
    private static final String ARROW = "/menuImgs/selector.png";
    private final SelectList sl = new SelectListJavafx();
    private final ImageView newRun;
    private final ImageView option;

    /**
     * Create the save menu.
     * @param selector the {@link SubMenuSelection}.
     * @param pnMain the {@link Pane} that contains the other @param.
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
            Platform.runLater(() -> sl.setDistance(new Pair<Double, Double>(-imgSelector.getBoundsInParent().getWidth(), imgSelector.getBoundsInParent().getHeight())));
        });

        newRun.setImage(new Image(NEWRUN));
        option.setImage(new Image(OPTION));
        imgSelector.setImage(new Image(ARROW));
    }

    @Override
    public final void input(final Command c) {
        switch (c) {
        case ARROW_UP:
            up();
            break;
        case ARROW_DOWN:
            down();
            break;
        case ENTER:
            enter();
            break;
        case EXIT:
            exit();
            break;
         default:
        }
    }

    private void up() {
        sl.previous();
    }

    private void down() {
        sl.next();
    }

    private void enter() {
        if (sl.get().equals(newRun) && getSelector().contains(SubMenuRun.class)) {
            getSelector().selectSubMenu(SubMenuRun.class);
        }
        if (sl.get().equals(option) && getSelector().contains(SubMenuOption.class)) {
            getSelector().selectSubMenu(SubMenuOption.class);
        }
    }

    private void exit() {
        if (getSelector().contains(SubMenuEnter.class)) {
            getSelector().selectSubMenu(SubMenuEnter.class);
        }
    }

    @Override
    public final void reset() {
        sl.initial();
    }

}
