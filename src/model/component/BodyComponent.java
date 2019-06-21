package model.component;

import model.entity.Entity;
import util.NotEquals;
import util.Triplet;

/**
 * Component that contains the informations about the body of the entity, that
 * is its dimension and the position.
 */

public class BodyComponent extends AbstractComponent<BodyComponent> {

    @NotEquals
    private static final Triplet<Double, Double, Double> DEFAULT_POSITION_VALUE = new Triplet<>(0.0, 0.0, 0.0);
    private static final double DEFAULT_SCALABLE_VALUE = 1;
    private static final int DEFAULT_WEIGHT_VALUE = 1;
    private Triplet<Double, Double, Double> position;
    private Triplet<Double, Double, Double> previousPosition;
    private double height;
    private double width;
    private int weight;

    /**
     * Initialize the parameters.
     * 
     * @param position of the entity
     * @param height   of the entity
     * @param width    of the entity
     * @param weight   of the entity
     * @param entity   entity for this component
     */
    public BodyComponent(final Entity entity, final Triplet<Double, Double, Double> position, final double height,
            final double width, final int weight) {
        super(entity);
        this.position = position;
        this.previousPosition = position;
        this.height = height;
        this.width = width;
        this.weight = weight;
    }

    /**
     * Initialize the parameters.
     * 
     * @param x      position on the x axis
     * @param y      position on the y axis
     * @param z      position on the z axis
     * @param height of the entity
     * @param width  of the entity
     * @param weight of the entity
     * @param entity entity for this component
     */
    public BodyComponent(final Entity entity, final double x, final double y, final double z, final double height,
            final double width, final int weight) {
        super(entity);
        this.position = new Triplet<Double, Double, Double>(x, y, z);
        this.height = height;
        this.width = width;
        this.weight = weight;
    }

    /**
     * Default BodyComponent constructor.
     * 
     * @param entity entity for this component
     */
    public BodyComponent(final Entity entity) {
        super(entity);
        this.position = DEFAULT_POSITION_VALUE;
        this.width = DEFAULT_SCALABLE_VALUE;
        this.height = DEFAULT_SCALABLE_VALUE;
        this.weight = DEFAULT_WEIGHT_VALUE;
    }

    /**
     * Position getter.
     * 
     * @return position
     */
    public Triplet<Double, Double, Double> getPosition() {
        return this.position;
    }

    /**
     * Changes the position of the entity.
     * 
     * @param deltaX movement on the x axis
     * @param deltaY movement on the y axis
     * @param deltaZ movement on the z axis
     */
    protected void changePosition(final double deltaX, final double deltaY, final double deltaZ) {
        this.previousPosition = this.position;
        this.position = new Triplet<Double, Double, Double>(this.position.getV1() + deltaX,
                this.position.getV2() + deltaY, this.position.getV3() + deltaZ);
    }

    /**
     * Sets a new position for the entity.
     * 
     * @param newX the new position on the x axis
     * @param newY the new position on the y axis
     * @param newZ the new position on the z axis
     */
    protected void setPosition(final double newX, final double newY, final double newZ) {
        this.position = new Triplet<Double, Double, Double>(newX, newY, newZ);
    }

    /**
     * Sets a new position for the entity.
     * 
     * @param newPosition Triplet of the position
     */
    protected void setPosition(final Triplet<Double, Double, Double> newPosition) {
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
     * Scale the dimension of the entity (ex. doubles it).
     * 
     * @param heightFactor multiply factor of the height
     * @param widthFactor  multiply factor of the width
     */
    protected void scaleDimension(final double heightFactor, final double widthFactor) {
        this.height = this.height * heightFactor;
        this.width = this.width * widthFactor;
    }

    /**
     * Scale the dimension of the entity (ex. doubles it).
     * 
     * @param factor multiply factor of the height and width.
     */
    protected void scaleDimension(final double factor) {
        this.scaleDimension(factor, factor);
    }

    /**
     * 
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
    protected Triplet<Double, Double, Double> getPositionPrevious() {
        return this.previousPosition;
    }
}
