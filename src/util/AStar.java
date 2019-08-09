package util;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Class for the AStar search algorithm.
 */
public class AStar {
    private static final int DIAGONAL_COST = 14;
    private static final int V_H_COST = 10;
    private static final int MAXSTEPASTAR = 200;

  //Blocked cells are just null Cell values in grid
    private final Cell[][] grid;
    private final boolean[][] closed;
    private final Pair<Integer, Integer> gridSize;

    private final PriorityQueue<Cell> open;

    /**
     * Set the width and the height of the grid where search the path.
     * @param width the width of the grid.
     * @param height the height of the grid.
     */
    public AStar(final int width, final int height) {
        open = new PriorityQueue<>((Object o1, Object o2) -> ((Cell) o1).finalCost - ((Cell) o1).finalCost);
        grid = new Cell[width][height];
        closed = new boolean[width][height];
        gridSize = new Pair<>(width, height);
    }


    /**
     * Set the width and the height of the grid where search the path and the walls.
     * @param width the width of the grid.
     * @param height the height of the grid.
     * @param walls the walls.
     */
    public AStar(final int width, final int height, final List<Pair<Integer, Integer>> walls) {
        open = new PriorityQueue<>((Object o1, Object o2) -> ((Cell) o1).finalCost - ((Cell) o1).finalCost);
        grid = new Cell[width][height];
        closed = new boolean[width][height];
        walls.forEach(p -> setBlocked(p.getX(), p.getY()));
        gridSize = new Pair<>(width, height);
    }

    /**
     * Set a walls.
     * @param x coordinate of the wall. 
     * @param y coordinate of the wall.
     */
    public void setBlocked(final int x, final int y) {
        grid[x][y] = null;
    }

    private void checkAndUpdateCost(final Cell current, final Cell t, final int cost) {
        if (t == null || closed[t.i][t.j]) {
            return;
        }
        final int tFinalCost = t.heuristicCost + cost;
        final boolean inOpen = open.contains(t);
        if (!inOpen || tFinalCost < t.finalCost) {
            t.finalCost = tFinalCost;
            t.parent = current;
            if (!inOpen) {
                open.add(t);
            }
        }
    }

    /**
     * Calculate a path.
     * @param start set the start point.
     * @param end set the destination.
     * @return the next node to go.
     */
    public Pair<Integer, Integer> calculate(final Pair<Integer, Integer> start, final Pair<Integer, Integer> end) {
        for (int i = 0; i < gridSize.getX(); ++i) {
            for (int j = 0; j < gridSize.getY(); ++j) {
                grid[i][j] = new Cell(i, j);
                grid[i][j].heuristicCost = Math.abs(i - end.getX()) + Math.abs(j - end.getY());
            }
         }
         grid[start.getX()][start.getY()].finalCost = 0;

        //add the start location to open list.
        Cell current = grid[start.getX()][start.getY()];
        open.add(current);
        int step = 0;
        while (step < MAXSTEPASTAR) {
            current = open.poll();
            if (current == null) {
                break;
            }
            closed[current.i][current.j] = true; 

            if (current.equals(grid[end.getX()][end.getY()])) {
                break; 
            } 

            Cell t;
            if (current.i - 1 >= 0) {
                t = grid[current.i - 1][current.j];
                checkAndUpdateCost(current, t, current.finalCost + V_H_COST); 

                if (current.j - 1 >= 0) {
                    t = grid[current.i - 1][current.j - 1];
                    checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
                }

                if (current.j + 1 < grid[0].length) {
                    t = grid[current.i - 1][current.j + 1];
                    checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST); 
                }
            } 

            if (current.j - 1 >= 0) {
                t = grid[current.i][current.j - 1];
                checkAndUpdateCost(current, t, current.finalCost + V_H_COST); 
            }

            if (current.j + 1 < grid[0].length) {
                t = grid[current.i][current.j + 1];
                checkAndUpdateCost(current, t, current.finalCost + V_H_COST); 
            }

            if (current.i + 1 < grid.length) {
                t = grid[current.i + 1][current.j];
                checkAndUpdateCost(current, t, current.finalCost + V_H_COST); 

                if (current.j - 1 >= 0) {
                    t = grid[current.i + 1][current.j - 1];
                    checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST); 
                }
                if (current.j + 1 < grid[0].length) {
                   t = grid[current.i + 1][current.j + 1];
                    checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST); 
                }
            }
            step++;
        }
        open.clear();
        clearClosed();

        if (current == null) {
            throw new IllegalStateException();
        }
        if (current.parent == null) {
            return new Pair<Integer, Integer>(current.i, current.j);
        }
        while (current.parent.parent != null) {
            current = current.parent;
        }
        System.out.println("\n" + start.getX() + " " + start.getY() + "\t" + end.getX() + " " + end.getY());
        return new Pair<Integer, Integer>(current.i, current.j);
    }
    private void clearClosed() {
        for (int i = 0; i < closed.length; i++) {
            for (int j = 0; j < closed[0].length; j++) {
                closed[i][j] = false;
            }
        }
    }
    private static class Cell {
        private int heuristicCost; //Heuristic cost
        private int finalCost;  //G+H
        private final int i;
        private final int j;
        private Cell parent; 

        Cell(final int i, final int j) {
            heuristicCost = 0;
            finalCost = 0;
            this.i = i;
            this.j = j; 
        }
        @Override
        public String toString() {
            return "[" + this.i + ", " + this.j + "]";
        }
    }
}
