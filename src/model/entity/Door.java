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
    private static final int DEFAULTZ = 0;
    private static final int DEFAULTHEIGHT = 100;
    private static final int DEFAULTWIDTH = 1;
    private static final int DEFAULTWEIGHT = 0;

    /**
     * Create a door based on the direction, destination.
     * @param location the direction of the door in the room (0= North; 1= East; 2= South; 3= West)
     * @param destinationIndex the room that this door conducts
     */
    public Door(final Integer location, final Integer destinationIndex) {
        super();
        attachComponent(new BodyComponent(this, destinationIndex, destinationIndex, destinationIndex, destinationIndex, destinationIndex, destinationIndex));
        attachComponent(new DoorComponent(this, location, destinationIndex));
    }


    
}
