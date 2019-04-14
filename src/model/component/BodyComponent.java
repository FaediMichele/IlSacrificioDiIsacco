package model.component;

import model.entity.Entity;

/**
 * Component that contains the informations about the body of the entity, that
 * is its dimension and the position.
 */

public class BodyComponent extends AbstractComponent<BodyComponent> {

    private static final double DEFAULT_POSITION_VALUE = 0;
    private static final double DEFAULT_SCALABLE_VALUE = 1;
    private static final int DEFAULT_WEIGHT_VALUE = 1;
    private double x;
    private double y;
    private double z;
    private double height;
    private double width;
    private int weight;

    /**
     * Initialize the parameters.
     * 
     * @param x      position on the x axis
     * @param y      position on the y axis
     * @param z      position on the x axis
     * @param height of the entity
     * @param width  of the entity
     * @param weight of the entity
     * @param entity      entity for this component
     */
    public BodyComponent(final Entity entity, final double x, final double y, final double z, final double height,
            final double width, final int weight) {
        super(entity);
        this.x = x;
        this.y = y;
        this.z = z;
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
        this.x = DEFAULT_POSITION_VALUE;
        this.y = DEFAULT_POSITION_VALUE;
        this.z = DEFAULT_POSITION_VALUE;
        this.width = DEFAULT_SCALABLE_VALUE;
        this.height = DEFAULT_SCALABLE_VALUE;
        this.weight = DEFAULT_WEIGHT_VALUE;
    }

    /**
     * X getter.
     * 
     * @return x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Y getter.
     * 
     * @return y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Z getter.
     * 
     * @return z
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Changes the position of the entity.
     * 
     * @param deltaX movement on the x axis
     * @param deltaY movement on the y axis
     * @param deltaZ movement on the z axis
     */
    protected void changePosition(final double deltaX, final double deltaY, final double deltaZ) {
        this.x = this.x + deltaX;
        this.y = this.y + deltaY;
        this.z = this.z + deltaZ;
    }

    /**
     * Sets a new position for the entity.
     * 
     * @param newX the new position on the x axis
     * @param newY the new position on the y axis
     * @param newZ the new position on the z axis
     */
    protected void setPosition(final double newX, final double newY, final double newZ) {
        this.x = newX;
        this.y = newY;
        this.z = newZ;
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
}
