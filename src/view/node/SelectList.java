package view.node;

import javafx.util.Pair;

/**
 * This interface is used for the selected items from a list. 
 * Near the selected node appear a special node that indicates witch items is selected.
 */
public interface SelectList {

    /**
     * Pass to the next item.
     */
    void next();

    /**
     * Go to the previous item.
     */
    void previous();

    /**
     * Got to the initial item.
     */
    void initial();

    /**
     * Get the selected item. 
     * @return the selected item
     */
    Object get();

    /**
     * Add items to the list.
     * @param items items to add to the list.
     */
    void addItems(Object... items);

    /**
     * Set the item that indicate witch item is selected.
     * @param selector the items to move to the selected item.
     */
    void setSelector(Object selector);

    /**
     * Set how distance is the selector from the selected item.
     * @param distance distance from the selector
     */
    void setDistance(Pair<Double, Double> distance);
}
