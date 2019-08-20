package view.node.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.node.TranslationPages;

/**
 * The implementation for JavaFx of {@link TranslationPages}.
 * There is one main page and all other pages goes in front of it.
 */
public class ContextPageJavafx implements TranslationPages {
    private final List<Pane> panes;
    private final Pane mainPane;
    private Pane selected;
    private TranslateTransition selectedTransition;

    private final List<TranslateTransition> tts; 
    private final long milliseconds;

    /**
     * Create a ContextPageJavafx with the time for the animation.
     * @param staticPane the pane that stay in from of others.
     * @param milliseconds the time for the animation.
     */
    public ContextPageJavafx(final Pane staticPane, final long milliseconds) {
        panes = new ArrayList<Pane>();
        tts = new ArrayList<>();
        this.mainPane = staticPane;
        this.milliseconds = milliseconds;
        selected = staticPane;
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
            tts.add(new TranslateTransition(Duration.millis(milliseconds), (Pane) pages[i]));
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
        tts.forEach(t -> t.setDuration(Duration.millis(ms)));
    }

    /**
     * Start the animation of all inserted pane to make the parameter the one on the window.
     * @param page the {@link Page} of destination.
     */
    @Override
    public void goTo(final Object page) {
        if (page == null || page.equals(selected)) {
            return;
        }
        if (!panes.contains((Pane) page)) {
            throw new IllegalArgumentException("page not found");
        }
        if (selected != null && page.equals(mainPane)) {
            selectedTransition.setToY(0);
            selected = mainPane;
        } else {
            if (selectedTransition != null) {
                selectedTransition.setToY(0);
                selectedTransition.play();
            }
            selected = (Pane) page;
            selectedTransition = tts.stream().filter(t -> t.getNode().equals(selected))
                    .findFirst().get();
            selectedTransition.setToX(-selected.getLayoutX() + mainPane.getLayoutX() + mainPane.getWidth() / 2 - selected.getWidth() / 2);
            selectedTransition.setToY(-selected.getLayoutY() + mainPane.getLayoutY() + mainPane.getHeight() / 2 - selected.getHeight() / 2);
        }
        selectedTransition.playFromStart();
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
            throw new IllegalArgumentException("page not found");
        }
        if (selected != null && page.equals(mainPane)) {
            selected.setTranslateY(0);
            selected = mainPane;
        } else {
            selected = (Pane) page;
            selectedTransition = tts.stream().filter(t -> t.getNode().equals(selected))
                    .findFirst().get();
            selected.setTranslateX(-selected.getLayoutX() + mainPane.getLayoutX() + mainPane.getWidth() / 2 - selected.getWidth() / 2);
            selected.setTranslateY(-selected.getLayoutY() + mainPane.getLayoutY() + mainPane.getHeight() / 2 - selected.getHeight() / 2);
        }
    }
}
