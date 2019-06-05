package view.javafx;

import view.SubMenu;
import view.SubMenuSelection;
import view.node.TranslationPageJavafx;
import view.node.TranslationPages;

/**
 * The selector for JavaFx.
 * It manage the animation for change the sub menu.
 */
public class SubMenuSelectionJavafx extends SubMenuSelection {
    private final TranslationPages tp;

    /**
     * Create the {@link SubMenuSelectionJavafx}. 
     * @param ms the time for the animation.
     */
    public SubMenuSelectionJavafx(final long ms) {
        super();
        tp = new TranslationPageJavafx(ms);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goTo(final SubMenu start, final SubMenu end) {
        if (!tp.contains(start.getMain())) {
            tp.addPage(start.getMain());
        }
        if (!tp.contains(end.getMain())) {
            tp.addPage(end.getMain());
        }
        tp.goTo(end.getMain());
    }

}
