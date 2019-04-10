package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.Component;
import model.component.DoorComponent;
import model.entity.events.EventListener;
import model.entity.events.DoorChangeEvent;
import util.Lambda;

/**
 * Implements the doors.
 */
public class Door extends AbstractStaticEntity {

    /**
     * Create a new door based on the direction and destination.
     * @param location the direction of the door in the room (0= North; 1= East; 2= South; 3= West)
     * @param destinationIndex the room that this door conducts
     */
    public Door(final Integer location, final Integer destinationIndex) {
        this(new BodyComponent(), new CollisionComponent(), location, destinationIndex);
    }

    /**
     * Create a door based on the body, collision, direction, destination.
     * @param entityBody The body of the door
     * @param entityCollision the collision of the door
     * @param location the direction of the door in the room (0= North; 1= East; 2= South; 3= West)
     * @param destinationIndex the room that this door conducts
     */
    public Door(final BodyComponent entityBody, final CollisionComponent entityCollision,
            final Integer location, final Integer destinationIndex) {
        super(entityBody, entityCollision);

        attachComponent(new DoorComponent(this, location, destinationIndex));
    }

    /**
     * Generate a door based on the position.
     * @param position 0=NORD, 1=EAST, 2=SUD, 3=OVEST
     * @param door the {@link DoorComponent} that define the Door
     */
    public Door(final int position, final DoorComponent door) {
        this(generateBody(position), generateCollision());
        attachComponent(door);
    }

    private Door(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        super(entityBody, entityCollision);
    }

    private static BodyComponent generateBody(final int position) {
        return new BodyComponent(0, 0, 0, 1, 1, 1);
    }
    private static CollisionComponent generateCollision() {
        return new CollisionComponent();
    }
}
