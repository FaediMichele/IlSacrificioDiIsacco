package model.component;

import com.google.common.eventbus.Subscribe;

import model.entity.Entity;
import model.events.CollisionEvent;
import model.events.DoorChangeEvent;
import model.events.EventListener;

/**
 * This component is used by the doors.
 *
 */
public class DoorComponent extends AbstractComponent<DoorComponent> {

    private final Integer destination;
    private final Integer location;
    private final boolean hasPlayerPassed;
    private final boolean locked;

    /**
     * Create a door component with a destination room index.
     * 
     * @param location The {@link Room} where the player is
     * @param destinationIndex index of the room
     * @param entity Entity that possess the component
     * @param locked says if the door is locked
     */
    public DoorComponent(final Entity entity, final Integer location, final Integer destinationIndex, final boolean locked) {
        super(entity);
        this.hasPlayerPassed = false;
        this.location = location;
        this.destination = destinationIndex;
        this.locked = locked;
        entity.registerListener(new EventListener<CollisionEvent>() {
            @Override
            @Subscribe
            public void listenEvent(final CollisionEvent event) {
                final CollisionEvent coll = (CollisionEvent) event;
                if (coll.getSourceEntity().hasComponent(HealthComponent.class)) {
                    if (locked && coll.getSourceEntity().hasComponent(KeychainComponent.class)) {
                           // ((KeychainComponent) coll.getSourceEntity().getComponent(KeychainComponent.class).get()).
                        System.out.println("TODO" + locked);
                    }
                    coll.getSourceEntity().getRoom().getFloor()
                    .changeEntityRoom(coll.getSourceEntity(), location, destination);
                    postPlayerPassed();
                }
            }
        });
    }

    /**
     * Verify if the player touched the door and is ready to change the
     * {@link Room}.
     * 
     * @return if the door is triggered
     */
    public boolean playerPassed() {
        System.out.println("TODO" + locked);
        return this.hasPlayerPassed;
    }

    /**
     * Get the {@link Room} that this door conducts.
     * 
     * @return the {@link Room} index
     */
    public Integer getDestination() {
        return this.destination;
    }

    /**
     * Post the event for the player that has passed.
     */
    private void postPlayerPassed() {
        this.getEntity().postEvent(new DoorChangeEvent(getEntity()));
    }

    /**
     * Get the room where the door is.
     * 
     * @return the location
     */
    public Integer getLocation() {
        return this.location;
    }

    @Override
    public final String toString() {
        return this.destination + " " + this.location + " " + this.hasPlayerPassed;
    }
}
