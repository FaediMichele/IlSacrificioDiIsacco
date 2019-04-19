package model.component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.entity.events.EventListener;
import model.entity.events.PickUpEvent;
import model.entity.events.ReleaseEvent;

/**
 * Keeps track of all the objects (which are entity themselves) that my entity
 * owns.
 */

public class InventoryComponent extends AbstractComponent<InventoryComponent> {

    private final Set<Entity> things;

    /**
     * Default InventoryComponent constructor.
     * 
     * @param entity {@link Entity} of this constructor
     */
    public InventoryComponent(final Entity entity) {
        super(entity);
        this.things = new HashSet<>();
        this.registListener();
    }

    /**
     * 
     * @param entity    to which the component belongs
     * @param component to be replaced with the new one that is being generated
     */
    public InventoryComponent(final Entity entity, final InventoryComponent component) {
        super(entity, component);
        this.things = component.getThings();
        this.registListener();
    }

    private void registListener() {
        registerListener(new EventListener<PickUpEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final PickUpEvent event) {
                final CollectibleComponent aux = (CollectibleComponent) event.getSourceEntity().getComponent(CollectibleComponent.class).get();
                aux.setEntityThatCollectedMe(getEntity());
                addThing(event.getSourceEntity());
                if (aux.needInitialized()) {
                    aux.init();
                }
                if (aux.isCollectible()) {
                    addThing(aux.getEntity());
                }
            }
        });

        registerListener(new EventListener<ReleaseEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final ReleaseEvent event) {
                if (thingsOfThisKind(event.getReleasedEntityClass()) != 0) {
                    final Entity thingToRelease = things.stream().filter(i -> i.getClass().equals(event.getReleasedEntityClass())).findAny().get();
                    final CollectibleComponent aux = (CollectibleComponent) thingToRelease.getComponent(CollectibleComponent.class).get();
                    if (aux.usable()) {
                        aux.use();
                    }
                    removeThing(thingToRelease);
                }
            }
        });
    }

    /**
     * The entity will disappear from the screen deactivating its body component.
     * 
     * @param thing to add
     */
    private void addThing(final Entity thing) {
        ((BodyComponent) thing.getComponent(BodyComponent.class).get()).setState(false);
        things.add(thing);
    }

    /**
     * The thing that has to be removed from the list because it has been used.
     * @param thing to remove
     */
    private void removeThing(final Entity thing) {
        things.remove(thing);
    }

    /**
     * 
     * @param thing
     * @return number of things of some kind (Es. number of bombs, number of keys)
     */
    private int thingsOfThisKind(final Class<? extends Entity> thingClass) {
        return (int) this.things.stream().filter(i -> i.getClass().equals(thingClass)).count();
    }

    /**
     * 
     * @return the list of things that have been collected
     */
    protected Set<Entity> getThings() {
        return Collections.unmodifiableSet(this.things);
    }
}
