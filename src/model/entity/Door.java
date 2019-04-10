package model.entity;

import model.component.BodyComponent;
import model.component.Component;
import model.component.DoorComponent;
import model.entity.events.EventListener;
import model.entity.events.DoorChangeEvent;
import util.Lambda;

/**
 * Create a door.
 * The doors have a position based on the location (North, East, ...).
 * The doors have a component called DoorComponent that contains the 
 */
public class Door extends AbstractStaticEntity {
    private static final int DEFAULTZ = 0;
    private static final int DEFAULTHEIGHT = 100;
    private static final int DEFAULTWIDTH = 1 / 16;
    private static final int DEFAULTWEIGHT = 0;

    /**
     * Create a door based on the direction, destination.
     * @param location the direction of the door in the room (0= North; 1= East; 2= South; 3= West)
     * @param destinationIndex the room that this door conducts
     */
    public Door(final Integer location, final Integer destinationIndex) {
        super();
        attachComponent(generateBody(location));
        attachComponent(new DoorComponent(this, location, destinationIndex));
    }

    /**
     * Generate a body for the door.
     * @param location
     * @return
     */
    private Component generateBody(final Integer location) {
        switch (location) {
        case 0:
            return new BodyComponent(this, 1 / 2, 0, DEFAULTZ, DEFAULTHEIGHT, DEFAULTWIDTH, DEFAULTWEIGHT);
        case 1:
            return new BodyComponent(this, 1, 1 / 2, DEFAULTZ, DEFAULTHEIGHT, DEFAULTWIDTH, DEFAULTWEIGHT);
        case 2:
            return new BodyComponent(this, 1 / 2, 1, DEFAULTZ, DEFAULTHEIGHT, DEFAULTWIDTH, DEFAULTWEIGHT);
        case 3:
            return new BodyComponent(this, 0, 1 / 2, DEFAULTZ, DEFAULTHEIGHT, DEFAULTWIDTH, DEFAULTWEIGHT);
        default:
            throw new IllegalArgumentException();
        }
    }


}
