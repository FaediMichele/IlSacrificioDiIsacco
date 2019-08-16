package model.component;

import model.entity.Entity;
import model.util.Position;
import util.NotEquals;
import util.Triplet;

/**
 * Component that contains the informations about the body of the entity, that
 * is its dimension and the position.
 */

public class BodyComponent extends AbstractComponent {

    @NotEquals
    private static final Position DEFAULT_POSITION_VALUE = new Position(0.0, 0.0, 0.0);
    private static final double DEFAULT_SCALABLE_VALUE = 1;
    private static final int DEFAULT_WEIGHT_VALUE = 1;
    private Position position;
    private Position previousPosition;
    private double height;
    private double width;
    private int weight;

    /**
     * Default BodyComponent constructor.
     * 
     * @param entity entity for this component
     */
    public BodyComponent(final Entity entity) {
        this(entity, 
                DEFAULT_POSITION_VALUE, 
                DEFAULT_SCALABLE_VALUE, 
                DEFAULT_SCALABLE_VALUE, 
                DEFAULT_WEIGHT_VALUE);
    }

    /**
     * 
     * @param entity 
     * @param height 
     * @param width 
     * @param weight 
     */
    public BodyComponent(final Entity entity, final double height, final double width, final int weight) {
        this(entity,
                DEFAULT_POSITION_VALUE,
                height,
                width,
                weight);
    }
    /**
     * Initialize the parameters.
     * 
     * @param position of the entity
     * @param height   of the entity
     * @param width    of the entity
     * @param weight   of the entity
     * @param entity   entity for this component
     */
    public BodyComponent(final Entity entity, 
                        final Position position, 
                        final double height,
                        final double width, 
                        final int weight) {
        super(entity);
        this.position = position;
        this.previousPosition = position;
        this.height = height;
        this.width = width;
        this.weight = weight;
    }

    /**
     * Position getter.
     * 
     * @return position
     */
    public Position getPosition() {
        return new Position(this.position.getX(), this.position.getY(), this.position.getZ());
    }

    /**
     * Changes the position of the entity.
     * @param deltaPosition the movement that has been made
     */
    public void changePosition(final Position deltaPosition) {
        this.previousPosition = this.position;
        this.position.add(deltaPosition);
    }

    /**
     * Go to the previous position.
     */
    public void goPrevious() {
        this.position = previousPosition;
    }

    /**
     * Sets a new position for the entity.
     * 
     * @param newPosition Triplet of the position
     */
    public void setPosition(final Position newPosition) {
        this.previousPosition = this.position;
        this.position = newPosition;
    }

    /**
     * Height getter.
     * 
     * @return height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Width getter.
     * 
     * @return width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Set the dimension of the entity.
     * @param width the height
     * @param height the width
     */
    public void setDimension(final double height, final double width) {
        this.height = height;
        this.width = width;
    }

    /**
     * Scale the dimension of the entity (ex. doubles it).
     * 
     * @param heightFactor multiply factor of the height
     * @param widthFactor  multiply factor of the width
     */
    public void scaleDimension(final double heightFactor, final double widthFactor) {
        this.height = this.height * heightFactor;
        this.width = this.width * widthFactor;
    }

    /**
     * Scale the dimension of the entity (ex. doubles it).
     * 
     * @param factor multiply factor of the height and width.
     */
    public void scaleDimension(final double factor) {
        this.scaleDimension(factor, factor);
    }

    /**
     *  Weigh getter.
     * @return weight
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * 
     * @param deltaWeight increase or decrease of the weight
     */
    public void changeWeight(final int deltaWeight) {
        this.weight = this.weight + deltaWeight;
    }

    /**
     * 
     * @return the position to the previous frame.
     */
    public Triplet<Double, Double, Double> getPreviousPosition() {
        return new Position(this.previousPosition.getX(), this.previousPosition.getY(),
                this.previousPosition.getZ());
    }
}
