package model.entity;

import model.component.BodyComponent;
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
    private static final Double DEFAULT_DIM1 = 40.0;
    private static final Double DEFAULT_DIM2 = 25.0;
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
       this.attachComponent(new DoorComponent(this, location, destinationIndex, BasicStatusEnum.OPEN))
                .attachComponent(new NeutralMentalityComponent(this));
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
        case DOWN:
            b.setDimension(DEFAULT_DIM2, DEFAULT_DIM1);
            b.setPosition(new Position(size.getX() / 2 - b.getWidth() / 2, Wall.getDefaultHeight() - b.getHeight(), DEFAULTZ));
            break;
        case RIGHT:
            b.setDimension(DEFAULT_DIM1, DEFAULT_DIM2);
            b.setPosition(new Position(size.getX() - Wall.getDefaultWidth(), size.getY() / 2 - b.getHeight() / 2, DEFAULTZ));
            break;
        case UP:
            b.setDimension(DEFAULT_DIM2, DEFAULT_DIM1);
            b.setPosition(new Position(size.getX() / 2 - b.getWidth() / 2, size.getY() - Wall.getDefaultHeight(), DEFAULTZ));
            break;
        case LEFT:
            b.setDimension(DEFAULT_DIM1, DEFAULT_DIM2);
            b.setPosition(new Position(Wall.getDefaultWidth() - b.getWidth(), size.getY() / 2 - b.getHeight() / 2, DEFAULTZ));
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
