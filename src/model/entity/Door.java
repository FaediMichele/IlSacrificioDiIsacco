package model.entity;

import model.component.BodyComponent;
import model.component.Component;
import model.component.DoorComponent;

/**
 * Implements the doors.
 */
public class Door extends AbstractStaticEntity {


    /**
     * Generate a door based on the position.
     * @param position 0=NORD, 1=EAST, 2=SUD, 3=OVEST
     * @param door the {@link DoorComponent} that define the Door
     */
    public Door(final int position, final DoorComponent door) {
        super(generateBody(position), generateCollision());
        attachComponent(door);
    }

    private static BodyComponent generateBody(final int position) {
        return new BodyComponent(0, 0, 0, 1, 1, 1);
    }
    private static Component generateCollision() {
        return generateBody(0);
    }
}
