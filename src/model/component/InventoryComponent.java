package model.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.google.common.eventbus.Subscribe;

//import model.entity.Bomb;
import model.entity.Entity;
import model.entity.events.EventListener;
import model.entity.events.PickUpEvent;
import model.entity.events.ReleaseBombEvent;

/**
 * Keeps track of all the objects (which are entity themselves) that my entity
 * owns.
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
                final PickUpComponent aux = (PickUpComponent) event.getSourceEntity().getComponent(PickUpComponent.class).get();
                aux.setEntityThatCollectedMe(Optional.of(getEntity()));
                addThing(event.getSourceEntity());
                if (aux.needInitialized()) {
                    aux.init();
                }
            }
        });

        registerListener(new EventListener<ReleaseBombEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final ReleaseBombEvent event) {
                /* waiting till the bomb is created to uncomment this
                 * if (things.stream().anyMatch(i -> i.getClass().equals(Bomb.class))) {
                    Bomb bombToRelease = (Bomb) things.stream().filter(i -> i.getClass().equals(Bomb.class)).findAny().get();
                    BodyComponent bombBody = (BodyComponent) bombToRelease.getCommponent(BodyComponent.class).get();
                    BodyComponent myBody = (BodyComponent) this.getEntity().getCommponent(BodyComponent.class).get();
                    bombBody.setState(true);
                    bombBody.setPosition(myBody.getX(), myBody.getY(), myBody.getZ());
                    poi lo aggiungeremo alla lista di entità da far apparire;
                }
                */
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
        this.things.add(thing);
    }

    /**
     * 
     * @return the list of things that have been collected
     */
    protected List<Entity> getThings() {
        return Collections.unmodifiableList(this.things);
    }
}
