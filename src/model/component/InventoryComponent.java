package model.component;

import java.util.ArrayList;
import java.util.List;

import model.entity.Entity;

/**
 * Keeps track of all the objects (which are entity themselves) that my entity owns.
 */

public class InventoryComponent extends AbstractComponent {

    private final List<Entity> things;

    /**
     * Default InventoryComponent constructor.
     * 
     * @param e entity of this constructor
     */
    public InventoryComponent(final Entity e) {
        super(e);
        things = new ArrayList<>();
    }

    /**
     * The entity will disappear from the screen deactivating its body component.
     * @param thing to add
     */
    public void addThing(final Entity thing) {
        ((BodyComponent) thing.getComponent(BodyComponent.class).get()).setState(false);
        this.things.add(thing);
    }

    /**
     * 
     * 
     */
    public void releaseThing() {
        /*
         * TO DO
         */
    }
}
