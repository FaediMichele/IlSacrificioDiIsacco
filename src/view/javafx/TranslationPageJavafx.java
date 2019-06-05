package view.javafx;

import javafx.util.Duration;
import view.node.TranslationPages;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * The implementation for JavaFx of {@link TranslationPages}.
 */
public class TranslationPageJavafx implements TranslationPages {
    private final Map<Pane, TranslateTransition> mapPane;
    private double time;
    private Pane selected;

    /**
     * Create a TranslationPageJavafx with the time for the animation.
     * @param milliseconds the time for the animation.
     */
    public TranslationPageJavafx(final long milliseconds) {
        time = milliseconds;
        mapPane = new LinkedHashMap<>();
    }

    /**
     * Add a {@link Pane}.
     * @param pages the array of {@link Pane} to add.
     */
    @Override
    public void addPage(final Object... pages) {
        for (int i = 0; i < pages.length; i++) {
            if (!(pages[i] instanceof Pane)) {
                throw new IllegalArgumentException("Parameter must be Javafx Pane");
            }
        }
        for (int i = 0; i < pages.length; i++) {
            final TranslateTransition tt = new TranslateTransition(Duration.millis(time), (Node) pages[i]);
            mapPane.put((Pane) pages[i], tt);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final Object page) {
        return mapPane.containsKey(page);
    }

    /**
     * Update the time for the animation.
     * @param ms the new time for the animation.
     */
    @Override
    public void setMilliseconds(final long ms) {
        time = ms;
        mapPane.values().forEach(tt -> tt.setDuration(Duration.millis(time)));
    }

    /**
     * Start the animation of all inserted pane to make the parameter the one on the window.
     * @param page the {@link Page} of destination.
     */
    @Override
    public void goTo(final Object page) {
        if (!mapPane.containsKey((Pane) page)) {
            throw new IllegalArgumentException("page not found");
        }
        if (selected != null) {
            mapPane.values().forEach(tt -> tt.stop());
        }
        selected = (Pane) page;
        mapPane.values().forEach(tt -> {
            tt.setToX(-selected.getLayoutX());
            tt.setToY(-selected.getLayoutY());
            tt.play();
        });
    }

    /**
     * Get the selected {@link Pane}.
     * @return the selected {@link Pane}.
     */
    @Override
    public Object getSelected() {
        return selected;
    }
}
