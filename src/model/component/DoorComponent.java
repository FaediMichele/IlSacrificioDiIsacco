package model.component;

import model.entity.Entity;
import model.entity.events.CollisionEvent;
import model.entity.events.CollisionListener;
import model.entity.events.DoorChangeEvent;

/**
 * This component is used by the doors.
 *
 */
public class DoorComponent extends AbstractComponent {

    private final Integer destination;
    private final Integer location;
    private final boolean hasPlayerPassed;

    /**
     * Create a door component with a destination room index.
     * 
     * @param location         The {@link Room} where the player is
     * @param destinationIndex index of the room
     * @param entity                Entity that possess the component
     */
    public DoorComponent(final Entity entity, final Integer location, final Integer destinationIndex) {
        super(entity);
        this.hasPlayerPassed = false;
        this.location = location;
        this.destination = destinationIndex;
        entity.registerListener(new CollisionListener((c) -> {
            final CollisionEvent coll = (CollisionEvent) c;
            if (coll.getSourceEntity().hasComponent(HealthComponent.class)) {
                postPlayerPassed();
            }
        }));
    }

    /**
     * Verify if the player touched the door and is ready to change the
     * {@link Room}.
     * 
     * @return if the door is triggered
     */
    public boolean playerPassed() {
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
        getEntity().postEvent(new DoorChangeEvent(getEntity()));
    }

    /**
     * Get the room where the door is.
     * 
     * @return the location
     */
    public Integer getLocation() {
        return location;
    }

}
