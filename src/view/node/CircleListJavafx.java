package view.node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * JavaFx node that circle through elements with an animation.
 * 
 * It have a width and height (ellipse). 
 */
public class CircleListJavafx extends Group implements CircleList {
    private final double height;
    private final double width;
    private final double dScale;
    private Duration d;
    private int selectedNode;
    private final LinkedList<MyNode> elements = new LinkedList<>();

    /**
     * Create a new {@link CircleListJavafx} with dimension and scaleMultiplier.
     * @param width the with of the ellipse of elements
     * @param height the height of the ellipse of elements
     * @param scaleMultiplier a scale multiplier for elements(far elements is smaller).
     */
    public CircleListJavafx(final double width, final double height, final double scaleMultiplier) {
        super();
        this.height = height;
        this.width = width;
        this.dScale = scaleMultiplier;
        selectedNode = 0;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void addElement(final Object o) {
        Objects.requireNonNull(o);
        if (!(o instanceof Node)) {
            throw new IllegalArgumentException("Parameter must be a node");
        }
        this.getChildren().add((Node) o);
        elements.add(new MyNode((Node) o, this.calculateAngle(elements.size() + 1, elements.size()), this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAll(final Object... o) {
        for (int i = 0; i < o.length; i++) {
            if (!(o[i] instanceof Node)) {
                throw new IllegalArgumentException("All parameter must be a node");
            }
        }
        for (int i = 0; i < o.length; i++) {
            this.getChildren().add((Node) o[i]);
            elements.add(new MyNode((Node) o[i], this.calculateAngle(elements.size() + 1, elements.size()), this));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rotateLeft() {
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).changeAngle(calculateAngle(elements.size(), i));
        }
        selectedNode = (selectedNode + 1) % elements.size();
        elements.addLast(elements.removeFirst());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rotateRight() {
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).changeAngle(calculateAngle(elements.size(), i));
        }
        selectedNode = (selectedNode - 1) % elements.size();
        elements.addFirst(elements.removeFirst());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDuration(final Object d) {
        Objects.requireNonNull(d);
        if (!(d instanceof Duration)) {
            throw new IllegalArgumentException("Parameter time must be" + Duration.class);
        }
        this.d = (Duration) d;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getElement() {
        return elements.get(selectedNode).node;
    }

    private float calculateAngle(final int numberNode, final int nodeIndex) {
        return (nodeIndex * 360 / numberNode) % 360;
    }

    /**
     * This class is used to make a circle effect. Rectangle have position and scale based on their angle in the circle.
     * This is for develop and learning purpose.
     */
    private static class MyNode {
        private final CircleListJavafx upper;

        private float angle;
        private final Node node;
        private final ScaleTransition st;
        private final TranslateTransition tt;

        /**
         * Create a new rectangle with defined angle.
         * @param r
         * @param angle the angle on the circle
         */
        MyNode(final Node r, final float angle, final CircleListJavafx upper) {
            this.node = r;
            this.upper = upper;
            this.angle = angle;
            this.node.setScaleX(getScale());
            this.node.setScaleY(getScale());
            st = new ScaleTransition(upper.d, node);
            tt = new TranslateTransition(upper.d, node);
        }

        private int getX() {
            return (int) Math.round(upper.getLayoutX() + Math.cos(Math.toRadians(angle)) * upper.width + node.getBoundsInLocal().getWidth() / 2);
        }

        private int getY() {
            return (int) Math.round(upper.getLayoutY() - Math.sin(Math.toRadians(angle)) * upper.height - node.getBoundsInLocal().getHeight() / 2);
        }

        private double getScale() {
            return 1 - Math.sin(Math.toRadians(angle)) * upper.dScale;
        }

        private void changeAngle(final float angle) {
            st.stop();
            tt.stop();
            tt.setFromX(getX());
            tt.setFromY(getY());
            st.setFromX(getScale());
            st.setFromY(getScale());
            this.angle = angle;
            tt.setToX(getX());
            tt.setToY(getY());
            st.setToX(getScale());
            st.setToY(getScale());
            st.play();
            tt.play();
        }
    }
}
