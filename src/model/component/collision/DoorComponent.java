package model.component.collision;

import model.entity.Entity;
import model.events.DoorChangeEvent;
/**
 * 
 *Component that manages the collision of the door.
 */
public class DoorComponent extends LockCollisionComponent {
    private final Integer destination;
    private final Integer location;
    private boolean hasPlayerPassed;

    /**
     * Create a door component with a destination room index.
     * 
     * @param locationIndex The {@link Room} where the player is
     * @param destinationIndex index of the room
     * @param entity Entity that possess the component
     */
    public DoorComponent(final Entity entity, final Integer locationIndex, final Integer destinationIndex) {
        super(entity);
        this.hasPlayerPassed = false;
        this.location = locationIndex;
        this.destination = destinationIndex;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    protected void afterUnlocks(final Entity entity) {
        entity.getRoom().getFloor()
        .changeEntityRoom(entity, location, destination);
        postPlayerPassed();
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
        this.getEntity().postEvent(new DoorChangeEvent(getEntity()));
        this.hasPlayerPassed = false;
    }

    /**
     * Get the room where the door is.
     * 
     * @return the location
     */
    protected Integer getLocation() {
        return this.location;
    }

    @Override
    public final String toString() {
        return this.destination + " " + this.location + " " + this.hasPlayerPassed;
    }
}
