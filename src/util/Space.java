package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * This class is used to calculate the collision.
 */
public class Space {
    private static final int MAXSTEPASTAR = 20;
    private static final int GRIDDEFINITION = 30;

    private final List<Rectangle> rs = new ArrayList<>();
    private final List<Rectangle> wall = new ArrayList<>();
    private Matrix<AStarCell> grid;
    private Pair<Double, Double> cellDim;

    /**
     * Add a {@link Rectangle} to the space.
     * @param r the {@link Rectangle} to add.
     * @param passable indicate if the node is a wall or not.
     */
    public void addRectangle(final Rectangle r, final boolean passable) {
        addRectangle(r);
        if (!passable) {
            wall.add(r);
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
        if (grid == null) {
            initMatrix();
        }
        final PriorityQueue<AStarCell> toVisit = new PriorityQueue<>(1, (a, b) -> Double.compare(a.getValue(), b.getValue()));
        final PriorityQueue<AStarCell> visited = new PriorityQueue<>(1, (a, b) -> Double.compare(a.getValue(), b.getValue()));
        final AStarCell startingCell = new AStarCell((int) (start.x / cellDim.getX()), (int) (start.y / cellDim.getY()));
        final double distanceInVert = cellDim.getY();
        final double distanceInHor = cellDim.getX();
        final double distanceInOblq = Math.sqrt(Math.pow(cellDim.getX(), 2) + Math.pow(cellDim.getY(), 2));
        int step = 0;
        toVisit.add(startingCell);
        while (step < MAXSTEPASTAR) {
            final AStarCell current = toVisit.remove();
            if ((int) (end.x / cellDim.getX()) == current.posX && (int) (end.y / cellDim.getY()) == current.posY) {
                break;
            }
            visited.add(current);
            // Neighbors
            if (current.posX > 0) {
   /* WEST*/    operationForNeighbour(toCell(current.posX - 1, current.posY, end, current, distanceInHor), current, toVisit, visited, distanceInHor);
                if (current.posY > 0) {
   /* NOTH-WEST*/   operationForNeighbour(toCell(current.posX - 1, current.posY - 1, end, current, distanceInOblq), current, toVisit, visited, distanceInOblq);
                }
                if (current.posY < grid.getHeight() - 1) {
   /* SOUTH-WEST*/  operationForNeighbour(toCell(current.posX - 1, current.posY + 1, end, current, distanceInOblq), current, toVisit, visited, distanceInOblq);
                }
            }
            if (current.posY > 0) {
   /* NOTH */   operationForNeighbour(toCell(current.posX, current.posY - 1, end, current, distanceInVert), current, toVisit, visited, distanceInVert);
            }
            if (current.posX < grid.getWidth() - 1) {
   /* EAST*/    operationForNeighbour(toCell(current.posX + 1, current.posY, end, current, distanceInHor), current, toVisit, visited, distanceInHor);
                if (current.posY > 0) {
   /* NORTH-EAST*/  operationForNeighbour(toCell(current.posX + 1, current.posY - 1, end, current, distanceInOblq), current, toVisit, visited, distanceInOblq);
                }
                if (current.posY < grid.getHeight() - 1) {
   /* SOUTH-EAST*/  operationForNeighbour(toCell(current.posX + 1, current.posY + 1, end, current, distanceInOblq), current, toVisit, visited, distanceInOblq);
                }
            }
            if (current.posY < grid.getHeight() - 1) {
   /* SOUTH*/   operationForNeighbour(toCell(current.posX, current.posY + 1, end, current, distanceInVert), current, toVisit, visited, distanceInVert);
            }
            step++;
        }
        grid.setToPredicate(null, c -> !c.isWall());
        final AStarCell ret =  visited.stream().filter(c -> c.parent != c).min((a, b) -> Double.compare(a.j, b.j)).get();
        return new Pair<Double, Double>(Double.valueOf(ret.posX), Double.valueOf(ret.posY));
    }

    private void operationForNeighbour(final AStarCell c, final AStarCell current, final PriorityQueue<AStarCell> toVisit,
            final PriorityQueue<AStarCell> visited, final double distanceToParent) {
        if (!c.isWall() && !visited.contains(c) && (!toVisit.contains(c) || c.getValue() > current.getValue() + distanceToParent)) {
            c.update(current.getValue() + distanceToParent, current);
            if (!toVisit.contains(c)) {
                toVisit.add(c);
            }
        }
    }

    private AStarCell toCell(final int posX, final int posY, final Rectangle end, final AStarCell parent, final double distFromParent) {
        final double distanceToEnd = Math.sqrt(Math.pow(posX - end.x - end.w / 2, 2) + Math.pow(posY - end.y - end.h / 2, 2));
        if (parent != null) {
            return new AStarCell(distanceToEnd, distFromParent + parent.getValue(),
                    parent, posX, posY);
        }
        return null;
    }
    private void initMatrix() {
        final int dimXMat = (int) (1 + wall.stream().max((a, b) -> Double.compare(a.x, b.x)).get().x
                / GRIDDEFINITION);
        final int dimYMat = (int) (1 + wall.stream().max((a, b) -> Double.compare(a.y, b.y)).get().y
                / GRIDDEFINITION);
        grid = new Matrix<AStarCell>(dimXMat, dimYMat);
        cellDim = new Pair<Double, Double>(Double.valueOf(dimXMat) / wall.size(), Double.valueOf(dimYMat) / wall.size());
        wall.forEach(r -> {
            grid.set((int) (r.x / cellDim.getX()), (int) (r.y / cellDim.getY()), new AStarCell((int) (r.x / cellDim.getX()), (int) (r.y / cellDim.getY())));
        });
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

    private static final class AStarCell {
        private double i;
        private double j;
        private AStarCell parent;
        private final boolean wall; 
        private final int posX;
        private final int posY;
        protected AStarCell(final double i, final double j, final AStarCell parent, final int posX, final int posY) {
            this.i = i;
            this.j = j;
            this.parent = parent;
            if (this.parent == null) {
                this.parent = this;
            }
            wall = false;
            this.posX = posX;
            this.posY = posY;
        }

        protected AStarCell(final int posX, final int posY) {
            wall = true;
            this.posX = posX;
            this.posY = posY;
        }

        public void update(final double j, final AStarCell parent) {
            this.j = j;
            this.parent = parent;
        }
        public double getValue() {
            return i + j;
        }

        public boolean isWall() {
            return wall;
        }
    }

}
