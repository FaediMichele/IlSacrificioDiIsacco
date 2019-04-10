package model.component;

import model.entity.Entity;

/**
 * Component that contains the informations about the body of the entity, that is its dimension and the position.
 *
 */

public class BodyComponent extends AbstractComponent {

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
     * @param x position on the x axis
     * @param y position on the y axis
     * @param z position on the x axis
     * @param height of the entity
     * @param width of the entity
     * @param weight of the entity
     * @param e entity for this component
     */
    public BodyComponent(final Entity e, final double x, final double y, final double z, final double height, final double width, final int weight) {
        super(e);
        this.x = x;
        this.y = y;
        this.z = z;
        this.height = height;
        this.width = width;
        this.weight = weight;
    }

    /**
     * Default BodyComponent constructor.
     * @param e entity for this component
     */
    public BodyComponent(final Entity e) {
        super(e);
        this.x = DEFAULT_POSITION_VALUE;
        this.y = DEFAULT_POSITION_VALUE;
        this.z = DEFAULT_POSITION_VALUE;
        this.width = DEFAULT_SCALABLE_VALUE;
        this.height = DEFAULT_SCALABLE_VALUE;
        this.weight = DEFAULT_WEIGHT_VALUE;
    }

    /**
     * X getter.
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * Y getter.
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * Z getter.
     * @return z
     */
    public double getZ() {
        return z;
    }

    /**
     * Changes the position of the entity.
     * @param deltaX movement on the x axis
     * @param deltaY movement on the y axis
     * @param deltaZ movement on the z axis
     */
    public void changePosition(final double deltaX, final double deltaY, final double deltaZ) {
        this.x = this.x + deltaX;
        this.y = this.y + deltaY;
        this.z = this.z + deltaZ;
    }

    /**
     * Height getter.
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Width getter.
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Scale the dimension of the entity (ex. doubles it).
     * @param heightFactor multiply factor of the height
     * @param widthFactor multiply factor of the width
     */
    public void scaleDimension(final double heightFactor, final double widthFactor) {
        this.height = this.height * heightFactor;
        this.width = this.width * widthFactor;
    }

    /**
     * 
     * @return weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * 
     * @param deltaWeight increase or decrease of the weight
     */
    public void changeWeight(final int deltaWeight) {
        this.weight = this.weight + deltaWeight;
    }

    @Override
    public void update(final Double deltaTime) {
        //?
    }
}
