package model.component.collision;

import java.util.Optional;

import model.component.BodyComponent;
import model.entity.Door;
import model.entity.Entity;
import model.enumeration.BasicMovementEnum;
import model.enumeration.BasicStatusEnum;
import model.events.DoorChangeEvent;
import model.util.Position;
import util.Pair;

/**
 * 
 * Component that manages the collision of the door.
 */
public class DoorComponent extends LockCollisionComponent {
    private static final int DISTANCE = 10;
    private final Integer destination;
    private final Integer location;
    private boolean lockForEnemy;

    /**
     * Create a door component with a destination room index.
     * 
     * @param locationIndex    The {@link Room} where the player is
     * @param destinationIndex index of the room
     * @param entity           Entity that possess the component
     * @param status           the status of the door.
     */
    public DoorComponent(final Entity entity, final Integer locationIndex, final Integer destinationIndex,
            final BasicStatusEnum status) {
        super(entity, !status.equals(BasicStatusEnum.OPEN));
        this.location = locationIndex;
        this.destination = destinationIndex;
        getEntity().getStatusComponent().setStatus(status);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void afterUnlocks(final Entity entity) {
        final BodyComponent eBody = entity.getComponent(BodyComponent.class).get();
        System.out.println("destinazione = "  + this.destination + " dimensione piano = " + entity.getRoom().getFloor().getRooms().size());
        System.out.println("stanza di partenza " + getEntity().getRoom().getIndex() + " = " + this.location);
        System.out.println("porte stanza di destinazione: (n = "  +  entity.getRoom().getFloor().getRooms().get(this.destination).getDoor().size() + ")");

        getEntity().getRoom().getFloor().getRooms().get(this.destination).getDoor()
        .stream()
        .map(d -> d.getComponent(DoorComponent.class).get())
        .map(dc -> new Pair<Integer, Integer>(dc.getLocation(), dc.getDestination()))
        .forEach(p -> {
            System.out.println("Destination = " + p.getY());
        });

        final Optional<? extends Door> optOtherDoor = getEntity().getRoom().getFloor()
                .getRooms().get(this.destination)
                .getDoor().stream()
                .filter(d -> d.getComponent(DoorComponent.class).get().getDestination().equals(this.location))
                .findFirst();
        System.out.println(optOtherDoor.isPresent());
        final Entity otherDoor = optOtherDoor.get();
        final BodyComponent otherBody = otherDoor.getComponent(BodyComponent.class).get();
        switch ((BasicMovementEnum) otherDoor.getStatusComponent().getMove()) {
        case DOWN:
            eBody.setPosition(new Position(
                    otherBody.getPosition().getX() + otherBody.getWidth() / 2 - eBody.getWidth() / 2,
                    otherBody.getPosition().getY() + otherBody.getHeight() + DISTANCE, eBody.getPosition().getZ()));
            break;
        case LEFT:
            eBody.setPosition(new Position(otherBody.getPosition().getX() + otherBody.getWidth() + DISTANCE,
                    otherBody.getPosition().getY() - otherBody.getHeight() / 2 + eBody.getHeight(),
                    eBody.getPosition().getZ()));
            break;
        case RIGHT:
            eBody.setPosition(new Position(otherBody.getPosition().getX() - eBody.getWidth() - DISTANCE,
                    otherBody.getPosition().getY() + otherBody.getHeight() / 2 - eBody.getHeight() / 2,
                    eBody.getPosition().getZ()));
            break;
        case UP:
            eBody.setPosition(
                    new Position(otherBody.getPosition().getX() + otherBody.getWidth() / 2 - eBody.getWidth() / 2,
                            otherBody.getPosition().getY() - eBody.getHeight() - DISTANCE, eBody.getPosition().getZ()));
            break;
        default:
            throw new IllegalStateException();
        }
        entity.getRoom().getFloor().changeEntityRoom(entity, location, destination);
        postPlayerPassed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Double deltaTime) {
        super.update(deltaTime);
        if (lockForEnemy && getEntity().getRoom().completed() && isLocked()) {
            this.unlocks(getEntity());
        }
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
    protected Integer getLocation() {
        return this.location;
    }
}
