package model.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.entity.Entity;

/**
 * Keeps track of all the objects (which are entity themselves) that my entity owns.
 */

public class InventoryComponent extends AbstractComponent<InventoryComponent> {

    private final List<Entity> things;

    /**
     * Default InventoryComponent constructor.
     * 
     * @param entity {@link Entity} of this constructor
     */
    public InventoryComponent(final Entity entity) {
        super(entity);
        this.things = new ArrayList<>();
    }
    /**
     * 
     * @param entity to which the component belongs
     * @param component to be replaced with the new one that is being generated
     */
    public InventoryComponent(final Entity entity, final InventoryComponent component) {
        super(entity, component);
        this.things = component.getThings();
    }


    /**
     * The entity will disappear from the screen deactivating its body component.
     * @param thing to add
     */
//    private void addThing(final Entity thing) {
//        ((BodyComponent) thing.getComponent(BodyComponent.class).get()).setState(false);
//        this.things.add(thing);
//    }

    /**
     * 
     * @return the list of things that have been collected
     */
    protected List<Entity> getThings() {
        return Collections.unmodifiableList(this.things);
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
