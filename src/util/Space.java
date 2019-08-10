package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is used to calculate the collision.
 */
public class Space {
    private static final int GRIDDEFINITION = 5;

    private final List<Rectangle> rs = new ArrayList<>();
    private final List<Rectangle> wall = new ArrayList<>();
    private final AStar algoritm;
    private final Pair<Double, Double> cellDim;

    /**
     * Create a new space with dimensions.
     * Dimensions is used for path finding, not for find collision.
     * @param width width of the space.
     * @param height height of the space.
     */
    public Space(final int width, final int height) {
        cellDim = new Pair<Double, Double>(Double.valueOf(((double) width) /  GRIDDEFINITION), Double.valueOf(((double) height) / GRIDDEFINITION));
        algoritm = new AStar(1 + (int) width / GRIDDEFINITION, 1 + (int) height / GRIDDEFINITION);
    }

    /**
     * Add a {@link Rectangle} to the space.
     * @param r the {@link Rectangle} to add.
     * @param passable indicate if the node is a wall or not.
     */
    public void addRectangle(final Rectangle r, final boolean passable) {
        addRectangle(r);
        if (!passable) {
            algoritm.setBlocked((int) r.x / GRIDDEFINITION, (int) r.y / GRIDDEFINITION);
            for (int i = 0;  i < r.w / GRIDDEFINITION; i++) {
                for (int k = 0;  k < r.h / GRIDDEFINITION; k++) {
                    algoritm.setBlocked(i + (int) r.x / GRIDDEFINITION, k + (int) r.y / GRIDDEFINITION);
                }
            }
        }
    }

    /**
     * Add a {@link Rectangle} to the space.
     * @param r the {@link Rectangle} to add.
     */
    public void addRectangle(final Rectangle r) {
        rs.add(r);
    }

    /**
     * Remove all the {@link Rectangle} to the space.
     */
    public void clear() {
        rs.clear();
    }

    /**
     * Remove a single {@link Rectangle} to the space.
     * @param r the {@link Rectangle} to remove.
     */
    public void remove(final Rectangle r) {
        rs.remove(r);
        wall.remove(r);
    }

    /**
     * return true if the space contains the {@link Rectangle}.
     * @param r {@link Rectangle} to verify if is contained in the space.
     * @return true if the space contains the {@link Rectangle}.
     */
    public boolean contains(final Rectangle r) {
        return rs.contains(r);
    }

    /**
     * Get all the collision found.
     * @return the list of rectangle touching each other.
     */
    public List<Pair<Rectangle, Rectangle>> getCollisions() {
        final List<Pair<Rectangle, Rectangle>> ret = new ArrayList<>();
        for (int i = 0; i < rs.size(); i++) {
            for (int k = i + 1; k < rs.size(); k++) {
                final Rectangle tmp = rs.get(k);
                if (rs.get(i).collide(tmp)) {
                    ret.add(new Pair<Rectangle, Rectangle>(rs.get(i), rs.get(k)));
                }
            }
        }
        return ret;
    }

    /**
     * Get all element in the array.
     * @return a list with all elements.
     */
    public List<Rectangle> getSpace() {
        return Collections.unmodifiableList(rs);
    }

    /**
     * Use an algorithm to find the next node for a route.
     * @param start the node where start the path finding route.
     * @param end the destination
     * @return the next 
     */
    public Pair<Double, Double> getNextNodePath(final Rectangle start, final Rectangle end) {
        final Pair<Integer, Integer> ret = algoritm.calculate(new Pair<Integer, Integer>((int) (start.x / GRIDDEFINITION), (int) (start.y / GRIDDEFINITION)),
                new Pair<Integer, Integer>((int) (end.x / GRIDDEFINITION), (int) (end.y / GRIDDEFINITION)));
        //return new Pair<Double, Double>(end.x, end.y);
        return new Pair<Double, Double>(Double.valueOf((int) ret.getX()) * GRIDDEFINITION, Double.valueOf((int) ret.getY()) * GRIDDEFINITION);
    }

    /**
     * A Rectangle in 2-D with x,y, width and height.
     * Provide a collision detection and distance.
     */
    public static class Rectangle {
        private double x;
        private double y;
        private final double w;
        private final double h;

        /**
         * Create a rectangle.
         * @param x the x
         * @param y the y
         * @param w the width
         * @param h the height
         */
        public Rectangle(final double x, final double y, final double w, final double h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        /**
         * Change the x.
         * @param x the new x.
         */
        public void setX(final double x) {
            this.x = x;
        }

        /**
         * Change the y.
         * @param y the new y.
         */
        public void setY(final double y) {
            this.y = y;
        }

        /**
         * Return true if this rectangle collide with another {@link Rectangle}.
         * @param r the other {@link Rectangle}
         * @return true if collide.
         */
        public boolean collide(final Rectangle r) { 
            return x + w >= r.x &&   // r1 right edge past r2 left
                x <= r.x + r.w &&    // r1 left edge past r2 right
                y + h >= r.y &&    // r1 top edge past r2 bottom
                y <= r.y + r.h;
        }

        /**
         * Get the distance to another {@link Rectangle}.
         * @param r the rectangle to calculate the distance.
         * @return the distance.
         */
        public double getDistanceTo(final Rectangle r) {
            return Math.sqrt(Math.pow(x + w / 2 - (r.x + r.w / 2), 2) + Math.pow(y + h / 2 - (r.y + r.h / 2), 2));
        }
    }
}
