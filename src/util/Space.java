package util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to calculate the collision.
 */
public class Space {
    private final List<Rectangle> rs = new ArrayList<>();

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
                if (rs.get(i).collide(rs.get(k)) && !ret.stream().anyMatch(p -> p.getX() == tmp)) {
                    ret.add(new Pair<Rectangle, Rectangle>(rs.get(i), rs.get(k)));
                }
            }
        }
        return ret;
    }

    /**
     * A rectangle.
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
    }
}
