package model.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.google.common.eventbus.Subscribe;
import model.component.collectible.AbstractCollectableComponent;
import model.component.collectible.AbstractPickupableComponent;
import model.entity.Entity;
import model.events.PickUpEvent;
import model.events.UseThingEvent;
import util.EventListener;

/**
 * Keeps track of all the objects (which are entity themselves) that my entity
 * owns.
 */

public class InventoryComponent extends AbstractComponent<InventoryComponent> {

    private static final int MAX_NUMBER_FOR_EACH_ITEM = 99;
    private final List<Entity> things;

    /**
     * Default InventoryComponent constructor.
     * 
     * @param entity {@link Entity} of this constructor
     */
    public InventoryComponent(final Entity entity) {
        super(entity);
        this.things = new ArrayList<Entity>();
        this.registerListeners();
    }

    /**
     * 
     * @param entity    to which the component belongs
     * @param component to be replaced with the new one that is being generated
     */
    public InventoryComponent(final Entity entity, final InventoryComponent component) {
        super(entity, component);
        this.things = component.getThings();
        this.registerListeners();
    }

    private void registerListeners() {
        this.registerListener(new EventListener<PickUpEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final PickUpEvent event) {
                final AbstractPickupableComponent oc = event.getSourceEntity()
                        .getComponent(AbstractPickupableComponent.class).get();
                oc.init(getEntity());
            }
        });

        this.registerListener(new EventListener<UseThingEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final UseThingEvent event) {
                if (thingsOfThisKind(event.getReleasedEntityClass()) != 0) {
                    final Optional<Entity> thingToRelease = things.stream()
                            .filter(i -> i.getClass().isInstance(event.getReleasedEntityClass())).findFirst();
                    if (thingToRelease.isPresent()) {
                        thingToRelease.get().getComponent(AbstractCollectableComponent.class).get().use();
                    }
                }
            }
        });

    }

    /**
     * The entity will disappear from the screen deactivating its body component.
     * 
     * @param thing thing to add
     * @return true if the entity has been collected correctly or false if it was
     *         not possible to collect the entity
     */
    public boolean addThing(final Entity thing) {
        if (this.thingsOfThisKind(thing.getClass()) < MAX_NUMBER_FOR_EACH_ITEM) {
            this.things.add(thing);
           // this.getEntity().getStatusComponent().setStatus(new Pair<Integer, String>(1, "pick up"));
            return true;
        }
        return false;
    }

    /**
     * The thing that has to be removed from the list and appear in the room.
     * 
     * @param thing to release
     */
    public void releaseThing(final Entity thing) {
        (thing.getComponent(BodyComponent.class).get())
                .setPosition((this.getEntity().getComponent(BodyComponent.class).get()).getPosition());
        this.getEntity().getRoom().insertEntity(thing);
//        this.getEntity().getStatusComponent().setStatus(new Pair<>(1, "appear"));
        this.things.remove(thing);
    }

    /**
     * The thing that has to be consumed (removed from the list).
     * 
     * @param thing to remove
     */
    public void consumeThing(final Entity thing) {
        this.things.remove(thing);
    }

    /**
     * 
     * @param thingClass it is the object of which we want to know the collected
     *                   quantities
     * @return number of things of some kind (Es. number of bombs, number of keys)
     */
    public int thingsOfThisKind(final Class<? extends Entity> thingClass) {
        return (int) this.things.stream().filter(i -> i.getClass().equals(thingClass)).count();
    }

    /**
     * 
     * @return the list of things that have been collected
     */
    public List<Entity> getThings() {
        return Collections.unmodifiableList(this.things);
    }
}
