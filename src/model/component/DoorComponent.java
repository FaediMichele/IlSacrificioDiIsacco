package model.component;

import java.util.Objects;

import model.entity.Entity;
import model.entity.events.DoorChangeEvent;
import model.entity.events.DoorChangeListener;
import model.entity.events.Event;
import model.entity.events.EventListener;

/**
 * This component is used by the doors.
 *
 */
public class DoorComponent extends AbstractComponent {

    private final Integer destination;
    private final Integer location;
    private boolean playerPassed = false;

    /**
     * Create a door component with a destination room index.
     * @param location The {@link Room} where the player is
     * @param destinationIndex index of the room
     */
    public DoorComponent(final Integer location, final Integer destinationIndex) {
        this.location = location;
        this.destination = destinationIndex;
        Entity e;
        Event changed = new DoorChangeEvent(e, this.getClass());
        e.postEvent(changed);
    }

    @Override
    public void init() {
        // TODO register the event when the player touch the entity
    }

    /**
     * Verify if the player touched the door and is ready to change the {@link Room}.
     * @return if the door is triggered
     */
    public boolean playerPassed() {
        return playerPassed;
    }

    /**
     * Get the {@link Room} that this door conducts.
     * @return the {@link Room} index
     */
    public Integer getDestination() {
        return destination;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(destination, location, playerPassed);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DoorComponent)) {
            return false;
        }
        DoorComponent other = (DoorComponent) obj;
        return Objects.equals(destination, other.destination) && Objects.equals(location, other.location)
                && playerPassed == other.playerPassed;
    }

}
