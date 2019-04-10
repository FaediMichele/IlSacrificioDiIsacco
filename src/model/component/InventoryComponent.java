package model.component;

import java.util.ArrayList;
import java.util.List;

import model.entity.Entity;

/**
 * 
 */

public class InventoryComponent extends AbstractComponent{

    private final List<CollectibleThing> things;
    /**
     * Default InventoryComponent constructor.
     * @param e entity of this constructor
     */
    public InventoryComponent(final Entity e) {
        super(e);
        things = new ArrayList<>();
    }

    /**
     * 
     * @param thing to add
     */
    public void addThing(final CollectibleThing thing) {
        this.things.add(thing);
    }

    /**
     * 
     * @param thing to remove
     */
    public void releaseThing(final CollectibleThing thing) {
        this.things.remove(thing);
    }
}
