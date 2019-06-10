package view.javafx;

import java.util.Objects;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import view.SubMenu;
import view.SubMenuSelection;
import view.node.TranslationPages;

/**
 * The selector for JavaFx.
 * It manage the animation for change the sub menu.
 */
public class SubMenuSelectionJavafx extends SubMenuSelection {
    private final TranslationPages tp;

    /**
     * Create the {@link SubMenuSelectionJavafx}. 
     * @param main the main pane that contains all sub menu node.
     * @param s the scene of the application.
     * @param ms the time for the animation.
     */
    public SubMenuSelectionJavafx(final Pane main, final Scene s, final long ms) {
        super();
        tp = new TranslationPageJavafx(main, s, ms);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goTo(final SubMenu start, final SubMenu end) {
        Objects.requireNonNull(end);
        if (start != null && !tp.contains(start.getMain())) {
            tp.addPage(start.getMain());
        }
        if (!tp.contains(end.getMain())) {
            tp.addPage(end.getMain());
        }
        tp.goTo(end.getMain());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void jumpTo(final SubMenu dest) {
        Objects.requireNonNull(dest);
        if (!tp.contains(dest.getMain())) {
            tp.addPage(dest.getMain());
        }
        tp.jumpTo(dest.getMain());
    }

}
