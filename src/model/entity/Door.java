package model.entity;

import model.component.BodyComponent;
import model.component.CollisionComponent;
import model.component.Component;
import model.component.DoorComponent;

/**
 * Implements the doors.
 */
public class Door extends AbstractStaticEntity {

    /**
     * Basic constructor.
     */
    public Door() {
        this(new BodyComponent(), new CollisionComponent());
    }

    /**
     * 
     * @param entityBody the {@link BodyComponent}
     * @param entityCollision the {@link CollisionComponent}
     */
    public Door(final BodyComponent entityBody, final CollisionComponent entityCollision) {
        super(entityBody, entityCollision);
        attachComponent(new DoorComponent(0,  1));
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

    private static BodyComponent generateBody(final int position) {
        return new BodyComponent(0, 0, 0, 1, 1, 1);
    }
    private static CollisionComponent generateCollision() {
        return new CollisionComponent();
    }
}
