package view.javafx;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import view.node.RotatingNode;

/**
 * The implementation of {@link RotatingNode} for JavaFx.
 */
public class RotatingNodeJavafx implements RotatingNode {
    private Node n;
    private RotateTransition rt;
    private Duration time;
    private double angle;

    /**
     * Set the node to rotate.
     * @param node must be a {@link javafx.scene.Node}.
     */
    @Override
    public void setNode(final Object node) {
        if (!(node instanceof Node)) {
            throw new IllegalArgumentException("The param must be a javafx.scene.Node");
        }
        n = (Node) node;
    }

    /**
     * Set the time for the animation.
     * @param milliseconds time the the animation.
     */
    @Override
    public void setMilliseconds(final long milliseconds) {
        time = Duration.millis(milliseconds);
    }

    /**
     * Set the angle to rotate.
     */
    @Override
    public void setMaxAngle(final double angle) {
        this.angle = angle;
    }

    /**
     * Start the animation.
     */
    @Override
    public void start() {
        if (rt == null) {
            rt = new RotateTransition(time, n);
            rt.setFromAngle(-angle);
            rt.setToAngle(angle);
            rt.setCycleCount(RotateTransition.INDEFINITE);
            rt.setAutoReverse(true);
        }
        rt.playFromStart();
    }

    /**
     * Stop the animation.
     */
    @Override
    public void stop() {
        rt.stop();
    }

}
