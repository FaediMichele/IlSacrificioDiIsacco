package model.component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    private final Set<Entity> things;

    /**
     * Default InventoryComponent constructor.
     * 
     * @param entity {@link Entity} of this constructor
     */
    public InventoryComponent(final Entity entity) {
        super(entity);
        this.things = new HashSet<>();
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
                final Optional<? extends Component> oc =  event.getSourceEntity().getComponent(AbstractCollectibleComponent.class);
                if (oc.isPresent()) {
                    final AbstractCollectibleComponent absCollComp = (AbstractCollectibleComponent) oc.get();
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
                                                                  .findAny();
                    if (!thingToRelease.isPresent()) {
                        throw new IllegalArgumentException();
                    }

                    final Optional<Component> oc = thingToRelease.get().getComponents()
                                                       .stream()
                                                       .filter(c -> c.getClass().getSuperclass().equals(AbstractCollectibleCollectableComponent.class))
                                                       .findFirst();
                    if (oc.isPresent()) {
                        final AbstractCollectibleCollectableComponent absCollCollComp = (AbstractCollectibleCollectableComponent) oc.get();
                        releaseThing(thingToRelease.get(), event.getSourceEntity());
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
     * The thing that has to be removed from the list because it has been used.
     * 
     * @param thing to remove
     */
    private void releaseThing(final Entity thing, final Entity releaser) {
        ((BodyComponent) thing.getComponent(BodyComponent.class).get()).setState(true);
        ((BodyComponent) thing.getComponent(BodyComponent.class).get())
                .setPosition(((BodyComponent) releaser.getComponent(BodyComponent.class).get()).getPosition());
        releaser.getRoom().insertEntity(thing);
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
    public Set<Entity> getThings() {
        return Collections.unmodifiableSet(this.things);
    }
}
