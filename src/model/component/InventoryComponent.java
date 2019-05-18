package model.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.events.EventListener;
import model.events.PickUpEvent;
import model.events.ReleaseEvent;

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
        this.registrListeners();
    }

    /**
     * 
     * @param entity    to which the component belongs
     * @param component to be replaced with the new one that is being generated
     */
    public InventoryComponent(final Entity entity, final InventoryComponent component) {
        super(entity, component);
        this.things = component.getThings();
        this.registrListeners();
    }

    private void registrListeners() {
        this.registerListener(new EventListener<PickUpEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final PickUpEvent event) {
                final Optional<? extends Component> oc =  event.getSourceEntity().getComponent(AbstractPickupableComponent.class);
                if (oc.isPresent()) {
                    final AbstractPickupableComponent absCollComp = (AbstractPickupableComponent) oc.get();
                    absCollComp.init(getEntity());
                }
            }
        });

        this.registerListener(new EventListener<ReleaseEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final ReleaseEvent event) {
                if (thingsOfThisKind(event.getReleasedEntityClass()) != 0) {
                    final Optional<Entity> thingToRelease = things.stream()
                                                                  .filter(i -> i.getClass().equals(event.getReleasedEntityClass()))
                                                                  .findFirst();
                    if (!thingToRelease.isPresent()) {
                        throw new IllegalArgumentException();
                    }

                    final Optional<Component> oc = thingToRelease.get().getComponents()
                                                       .stream()
                                                       .filter(c -> c.getClass().getSuperclass().equals(AbstractCollectableComponent.class))
                                                       .findFirst();
                    if (oc.isPresent()) {
                        final AbstractCollectableComponent absCollCollComp = (AbstractCollectableComponent) oc.get();
                        absCollCollComp.use();
                    }
                }
            }
        });
    }

    /**
     * The entity will disappear from the screen deactivating its body component.
     * 
     * @param thing thing to add
     * @return true if the entity has been collected correctly or false if it was not possible to collect the entity
     */
    protected boolean addThing(final Entity thing) {
        if (this.thingsOfThisKind(thing.getClass()) < MAX_NUMBER_FOR_EACH_ITEM) {
            ((BodyComponent) thing.getComponent(BodyComponent.class).get()).setState(false);
            this.things.add(thing);
            return true;
        }
        return false;
    }

    /**
     * The thing that has to be removed from the list and appear in the room.
     * @param thing to release
     */
    protected void releaseThing(final Entity thing) {
        ((BodyComponent) thing.getComponent(BodyComponent.class).get()).setState(true);
        ((BodyComponent) thing.getComponent(BodyComponent.class).get())
                .setPosition(((BodyComponent) this.getEntity().getComponent(BodyComponent.class).get()).getPosition());
        this.getEntity().getRoom().insertEntity(thing);
        this.things.remove(thing);
    }

    /**
     * The thing that has to be consumed (removed from the list).
     * @param thing to remove
     */
    protected void consumeThing(final Entity thing) {
        this.things.remove(thing);
    }

    /**
     * 
     * @param thingClass 
     * @return number of things of some kind (Es. number of bombs, number of keys)
     */
    protected int thingsOfThisKind(final Class<? extends Entity> thingClass) {
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
