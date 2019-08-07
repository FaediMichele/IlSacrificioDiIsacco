package view.javafx;

import javafx.util.Duration;
import view.node.TranslationPages;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * The implementation for JavaFx of {@link TranslationPages}.
 * The pages will slide to the selected one.
 */
public class SlidingPageJavafx implements TranslationPages {
    private final List<Pane> panes;
    private Pane selected;
    private final TranslateTransition tt; 
    private final Scene s; 

    /**
     * Create a SlidingPageJavafx with the time for the animation.
     * @param main the main pane that contains all the node that will slide.
     * @param s the scene of the application.
     * @param milliseconds the time for the animation.
     */
    public SlidingPageJavafx(final Pane main, final Scene s, final long milliseconds) {
        panes = new ArrayList<Pane>();
        tt = new TranslateTransition(Duration.millis(milliseconds), main);
        this.s = s;
    }

    /**
     * Add a {@link Pane}.
     * @param pages the array of {@link Pane} to add.
     */
    @Override
    public void addPage(final Object... pages) {
        for (int i = 0; i < pages.length; i++) {
            if (!(pages[i] instanceof Pane)) {
                throw new IllegalArgumentException("Parameter must be Javafx Pane[" + pages[i].getClass().toString() + "]");
            }
        }
        for (int i = 0; i < pages.length; i++) {
            panes.add((Pane) pages[i]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final Object page) {
        return panes.contains(page);
    }

    /**
     * Update the time for the animation.
     * @param ms the new time for the animation.
     */
    @Override
    public void setMilliseconds(final long ms) {
        tt.setDuration(Duration.millis(ms));
    }

    /**
     * Start the animation of all inserted pane to make the parameter the one on the window.
     * @param page the {@link Page} of destination.
     */
    @Override
    public void goTo(final Object page) {
        if (!panes.contains((Pane) page)) {
            throw new IllegalArgumentException("page not found");
        }
        if (selected != null) {
            tt.stop();
        }
        selected = (Pane) page;
        tt.setToX(s.getWidth() / 2 - selected.getWidth() / 2 - selected.getLayoutX());
        tt.setToY(s.getHeight() / 2 - selected.getHeight() / 2 - selected.getLayoutY());
        tt.playFromStart();
    }

    /**
     * Get the selected {@link Pane}.
     * @return the selected {@link Pane}.
     */
    @Override
    public Object getSelected() {
        return selected;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void jumpTo(final Object page) {
        if (page == null) {
            return;
        }
        if (!panes.contains((Pane) page)) {
            throw new IllegalArgumentException("page not found" + ((Pane) page).getId());
        }
        if (selected != null) {
            tt.stop();
        }
        selected = (Pane) page;
        tt.getNode().setTranslateX(s.getWidth() / 2 - selected.getWidth() / 2 - selected.getLayoutX());
        tt.getNode().setTranslateY(s.getHeight() / 2 - selected.getHeight() / 2 - selected.getLayoutY());
    }
}
