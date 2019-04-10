package model.component;

import java.util.Objects;

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
     * @param e                Entity that possess the component
     */
    public DoorComponent(final Entity e, final Integer location, final Integer destinationIndex) {
        super(e);
        this.hasPlayerPassed = false;
        this.location = location;
        this.destination = destinationIndex;
        e.registerListener(new CollisionListener((c) -> {
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

    @Override
    public final int hashCode() {
        return Objects.hash(destination, location, this.hasPlayerPassed);
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
        final DoorComponent other = (DoorComponent) obj;
        return Objects.equals(destination, other.destination) && Objects.equals(location, other.location)
                && this.hasPlayerPassed == other.hasPlayerPassed;
    }

    /**
     * Post the event for the player that has passed.
     */
    private void postPlayerPassed() {
        getEntity().postEvent(new DoorChangeEvent(getEntity(), this.getClass()));
    }

}
