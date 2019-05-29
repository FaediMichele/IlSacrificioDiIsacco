package view.node;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;
import util.Pair;

/**
 * Javafx node that pass the node in a list.
 *
 * See also {@link SelectList}
 */
public class SelectListJavafx extends Group implements SelectList {

    private final List<Node> elements = new LinkedList<>();
    private Node selector;
    private int selectedNode;
    private Pair<Double, Double> distance;

    /**
     * Create a new List of item in Javafx.
     */
    public SelectListJavafx() {
        super();
        selectedNode = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void next() {
        selectedNode = selectedNode < elements.size() ? selectedNode + 1 : 0;
        updateSelector();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void previous() {
        selectedNode = selectedNode > 0 ? elements.size() - 1 : selectedNode - 1;
        updateSelector();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get() {
        return elements.get(selectedNode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItems(final Object... items) {
        for (int i = 0; i < items.length; i++) {
            if (!(items[i] instanceof Node)) {
                throw new IllegalArgumentException("Every items must be a Javafx node");
            }
        }
        for (int i = 0; i < items.length; i++) {
            elements.add((Node) elements.get(i));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelector(final Object selector) {
        if (!(selector instanceof Node)) {
            throw new IllegalArgumentException("Selecter must be a Javafx node");
        }
        this.selector = (Node) selector;
        updateSelector();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDistance(final Pair<Double, Double> distance) {
        this.distance = distance;
        updateSelector();
    }

    private void updateSelector() {
        final Node n = (Node) get();
        selector.setLayoutX(n.getLayoutX() + distance.getX());
        selector.setLayoutY(n.getLayoutY() + distance.getY());
    }
}
