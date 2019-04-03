package model.component;

/**
 * Component that contains the informations about the body of the entity, that is its dimension and the position.
 * @author asialucchi
 *
 */

public class BodyComponent extends AbstractComponent {
    private double x;
    private double y;
    private double z;
    private double height;
    private double width;

    /**
     * Initialize the parameters.
     * @param x position on the x axis
     * @param y position on the y axis
     * @param z position on the x axis
     * @param height of the entity
     * @param width of the entity
     */
    public BodyComponent(final double x, final double y, final double z, final double height, final double width) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
        this.height = height;
        this.width = width;
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

}
