package view.node;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * This pane resize all children based on the initial value and maintain the proportion.
 */
public class ResizablePane extends AnchorPane {

    /**
     * Create a new RisizablePane.
     */
    public ResizablePane() {
        super();
        Platform.runLater(() -> widthProperty().addListener((obs, oldVal, newVal) -> {
            updateSize(this, new Rectangle2D(0d, 0d, (double) oldVal,
                    this.getHeight()), new Rectangle2D(0d, 0d, (double) newVal, getHeight()));
        }));
        Platform.runLater(() -> heightProperty().addListener((obs, oldVal, newVal) -> {
            updateSize(this, new Rectangle2D(0d, 0d, getWidth(),
                    (double) oldVal), new Rectangle2D(0d, 0d, getWidth(), (double) newVal));
        }));
    }

    private void updateSize(final Parent p, final Rectangle2D oldValue, final Rectangle2D newValue) {
        p.getChildrenUnmodifiable().forEach(n -> {
            AnchorPane.setLeftAnchor(n, AnchorPane.getLeftAnchor(n) * newValue.getWidth()
                    / oldValue.getWidth());
            AnchorPane.setTopAnchor(n, AnchorPane.getTopAnchor(n) * newValue.getHeight()
                    / oldValue.getHeight());
            AnchorPane.setRightAnchor(n, AnchorPane.getRightAnchor(n) * newValue.getWidth()
                    / oldValue.getWidth());
            AnchorPane.setBottomAnchor(n,  AnchorPane.getBottomAnchor(n) * newValue.getHeight()
                    / oldValue.getHeight());
            /*
            if (n instanceof Control) {
                final Control c = (Control) n;
                c.setPrefWidth(c.getPrefWidth() * newValue.getWidth()
                        / oldValue.getWidth());
                c.setPrefHeight(c.getPrefHeight() * newValue.getHeight()
                        / oldValue.getHeight());
            } else if (n instanceof ImageView) {
                final ImageView c = (ImageView) n;
                c.setFitWidth(c.getFitWidth() * newValue.getWidth()
                        / oldValue.getWidth());
                c.setFitHeight(c.getFitHeight() * newValue.getHeight()
                        / oldValue.getHeight());
            }
            if (n instanceof Parent) {
                updateSize((Parent) n, oldValue, newValue);
            }
            */
        });
    }
}
