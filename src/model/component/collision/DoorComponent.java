package model.component.collision;

import model.component.BodyComponent;
import model.entity.Entity;
import model.enumeration.BasicMovementEnum;
import model.events.DoorChangeEvent;
import model.util.Position;
/**
 * 
 *Component that manages the collision of the door.
 */
public class DoorComponent extends LockCollisionComponent {
    private static final int DISTANCE = 10;
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
        super(entity, false);
        this.hasPlayerPassed = false;
        this.location = locationIndex;
        this.destination = destinationIndex;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    protected void afterUnlocks(final Entity entity) {
        final BodyComponent eBody = entity.getComponent(BodyComponent.class).get();
        final Entity otherDoor = getEntity().
                getRoom().getFloor().getRooms()
                .stream().filter(r -> r.getIndex() == this.destination).findFirst().get().getDoor().stream()
                .filter(d -> d.getComponent(DoorComponent.class).get().destination.equals(location)).findFirst().get();
        final BodyComponent otherBody = otherDoor.getComponent(BodyComponent.class).get();
        switch ((BasicMovementEnum) otherDoor.getStatusComponent().getMove()) {
            case DOWN:
                eBody.setPosition(new Position(otherBody.getPosition().getX() + otherBody.getWidth() / 2 - eBody.getWidth() / 2,
                        otherBody.getPosition().getY() - eBody.getHeight() - DISTANCE, eBody.getPosition().getZ()));
                break;
            case LEFT:
                eBody.setPosition(new Position(otherBody.getPosition().getX() + eBody.getWidth() + DISTANCE,
                        otherBody.getPosition().getY() - otherBody.getHeight() / 2 + eBody.getHeight(), eBody.getPosition().getZ()));
                break;
            case RIGHT:
                eBody.setPosition(new Position(otherBody.getPosition().getX() - eBody.getWidth() - DISTANCE,
                        otherBody.getPosition().getY() - otherBody.getHeight() / 2 + eBody.getHeight() / 2, eBody.getPosition().getZ()));
                break;
            case UP:
                eBody.setPosition(new Position(otherBody.getPosition().getX() + otherBody.getWidth() / 2 - eBody.getWidth() / 2,
                        otherBody.getPosition().getY() + otherBody.getHeight() + DISTANCE, eBody.getPosition().getZ()));
                break;
            default:
                throw new IllegalStateException();
        }
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
        this.hasPlayerPassed = true;
    }

    /**
     * Get the room where the door is.
     * 
     * @return the location
     */
    protected Integer getLocation() {
        return this.location;
    }
}
