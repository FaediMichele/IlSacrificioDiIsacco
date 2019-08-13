package model.entity;

import model.component.BodyComponent;
import model.component.LockComponent;
import model.component.collision.DoorComponent;
import model.component.mentality.NeutralMentalityComponent;
import model.enumeration.BasicEntityEnum;
import model.enumeration.BasicMovementEnum;
import model.enumeration.BasicStatusEnum;
import model.enumeration.EntityEnum;
import model.util.Position;
import util.Pair;

/**
 * Create a door. The doors have a position based on the location (North, East,
 * ...). The doors have a component called DoorComponent that contains the
 */
public class Door extends AbstractStaticEntity {
    private static final Double DEFAULTZ = 0.0;
    private static final Double DEFAULTSIZE = 40.0;
    private static final Double DEFAULTSTROKE = 30.0;
    private static final EntityEnum ENTITY_NAME = BasicEntityEnum.DOOR;


    /**
     * Create a door based on the direction, destination.
     * 
     * @param direction the direction of the door in the room.
     * @param location Where the door is
     * @param destinationIndex the room that this door conducts
     * @param roomSize The size of the room.
     */
    public Door(final BasicMovementEnum direction, final Integer location, final Integer destinationIndex, final Pair<Double, Double> roomSize) {
        super();
       this.attachComponent(new DoorComponent(this, location, destinationIndex, BasicStatusEnum.CLOSED))
                .attachComponent(new NeutralMentalityComponent(this))
                .attachComponent(new LockComponent(this));
       this.getStatusComponent().setMove(direction);
       setBody(direction, roomSize);
    }

    /**
     * Generate a body for the door.
     * 
     * @param location
     * @return
     */
    private void setBody(final BasicMovementEnum direction, final Pair<Double, Double> size) {
        final BodyComponent b = (BodyComponent) this.getComponent(BodyComponent.class).get();
        switch (direction) {
        case UP:
            b.setDimension(DEFAULTSTROKE, DEFAULTSIZE);
            b.setPosition(new Position(size.getX() / 2 - b.getWidth(), size.getY(), DEFAULTZ));
            break;
        case RIGHT:
            b.setDimension(DEFAULTSIZE, DEFAULTSTROKE);
            b.setPosition(new Position(size.getX(), size.getY() / 2 - b.getHeight() / 2, DEFAULTZ));
            break;
        case DOWN:
            b.setDimension(DEFAULTSTROKE, DEFAULTSIZE);
            b.setPosition(new Position(size.getX() / 2 - b.getWidth(), 0.0, DEFAULTZ));
            break;
        case LEFT:
            b.setDimension(DEFAULTSIZE, DEFAULTSTROKE);
            b.setPosition(new Position(0.0, size.getY() / 2 - b.getHeight() / 2, DEFAULTZ));
            break;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnum getNameEntity() {
        return ENTITY_NAME;
    }
}
