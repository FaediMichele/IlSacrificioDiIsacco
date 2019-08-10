package util;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


/**
 * Class for the AStar search algorithm.
 */
public class AStar {
    // ONLY FOR DEBUG
    /*private final Pane newP = new Pane(); 
    private static final int PANEMULTIPLIER = 10;*/

    private static final int DIAGONAL_COST = 14;
    private static final int V_H_COST = 10;
    private static final int MAXSTEPASTAR = 2000;

  //Blocked cells are just null Cell values in grid
    private final Cell[][] grid;
    private final boolean[][] closed;
    private final Pair<Integer, Integer> gridSize;
    private final List<Pair<Integer, Integer>> walls = new ArrayList<>();

    private final PriorityQueue<Cell> open;

    /**
     * Set the width and the height of the grid where search the path.
     * @param width the width of the grid.
     * @param height the height of the grid.
     */
    public AStar(final int width, final int height) {
        open = new PriorityQueue<>((Object o1, Object o2) -> ((Cell) o1).finalCost - ((Cell) o2).finalCost);
        grid = new Cell[width][height];
        closed = new boolean[width][height];
        gridSize = new Pair<>(width, height);
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                grid[i][j] = new Cell(i, j);
            }
         }
    }


    /**
     * Set the width and the height of the grid where search the path and the walls.
     * @param width the width of the grid.
     * @param height the height of the grid.
     * @param walls the walls.
     */
    public AStar(final int width, final int height, final List<Pair<Integer, Integer>> walls) {
        open = new PriorityQueue<>((Object o1, Object o2) -> ((Cell) o1).finalCost - ((Cell) o2).finalCost);
        grid = new Cell[width][height];
        closed = new boolean[width][height];
        this.walls.addAll(walls);
        walls.forEach(p -> setBlocked(p.getX(), p.getY()));
        gridSize = new Pair<>(width, height);
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                grid[i][j] = new Cell(i, j);
            }
         }
    }

    /**
     * Set a walls.
     * @param x coordinate of the wall. 
     * @param y coordinate of the wall.
     */
    public void setBlocked(final int x, final int y) {
        if (x < 0 || y < 0 || x >= gridSize.getX() || y >= gridSize.getY()) {
            return;
        }
        walls.add(new Pair<Integer, Integer>(x, y));
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
                if (grid[i][j] != null) {
                    grid[i][j].finalCost = 0;
                    grid[i][j].parent = null;
                    grid[i][j].heuristicCost = (int) Math.round(Math.sqrt(Math.pow(i - end.getX(), 2) + Math.pow(j - end.getY(), 2)));
                }
            }
         }
        for (int i = 0; i < gridSize.getX(); ++i) {
            for (int j = 0; j < gridSize.getY(); ++j) {
                final Integer tmpI = i;
                final Integer tmpJ = j;
                if (walls.stream().anyMatch(p -> p.getX().equals(tmpI) && p.getY().equals(tmpJ))) {
                    grid[i][j] = null;
                }
            }
         }
        grid[start.getX()][start.getY()] = new Cell(start.getX(), start.getY());
        grid[start.getX()][start.getY()].finalCost = 0;
        grid[start.getX()][start.getY()].heuristicCost = (int) Math.round(Math.sqrt(Math.pow(start.getX() - end.getX(), 2) + Math.pow(start.getY() - end.getY(), 2)));

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

            if (current.i == end.getX() && current.j == end.getY()) {
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
            if (step == MAXSTEPASTAR) {
                throw new IllegalStateException();
            }
        }
        open.clear();
        //debug(start, end, current);
        clearClosed();

        if (current == null) {
            return end;
        }
        if (current.parent != null && current.parent.parent != null) {
            while (current.parent.parent.parent != null) {
                current = current.parent;
            }
        }
        //return end;
        return new Pair<Integer, Integer>(current.i, current.j);
    }

    // DEBUG WINDOW
    /*
    private void debug(final Pair<Integer, Integer> start, final Pair<Integer, Integer> end, final Cell path) {
        final Pane p = (Pane) ViewGetterUtil.getScene().getRoot();
        Cell c = path;
        Platform.runLater(() -> newP.getChildren().clear());
        for (int i = 0; i < gridSize.getX(); ++i) {
            for (int j = 0; j < gridSize.getY(); ++j) {
                final Rectangle r = new Rectangle();
                r.setX(i * PANEMULTIPLIER);
                r.setY(j * PANEMULTIPLIER);
                r.setHeight(PANEMULTIPLIER);
                r.setWidth(PANEMULTIPLIER);
                if (i == start.getX() && j == start.getY()) {
                    r.setStroke(Color.YELLOW);
                    r.setFill(Color.YELLOW);
                } else if (i == end.getX() && j == end.getY()) {
                    r.setStroke(Color.RED);
                    r.setFill(Color.RED);
                } else if (grid[i][j] == null) {
                    r.setStroke(Color.BLUE);
                    r.setFill(Color.BLUE);
                } else {
                    r.setStroke(Color.WHITE);
                    r.setFill(Color.WHITE);
                }
                Platform.runLater(() -> newP.getChildren().add(r));
            }
         }
        System.out.println(start.getX() + " " + start.getY());
        System.out.println(end.getX() + " " + end.getY());
        System.out.println("Path: ");
        if (c != null) {
            while (c.parent != null) {
                final Rectangle r = new Rectangle();
                r.setX(c.i * PANEMULTIPLIER);
                r.setY(c.j * PANEMULTIPLIER);
                r.setHeight(PANEMULTIPLIER);
                r.setWidth(PANEMULTIPLIER);
                r.setStroke(Color.PURPLE);
                r.setFill(Color.PURPLE);
                c = c.parent;
                Platform.runLater(() -> newP.getChildren().add(r));
                System.out.print("[" + c.i + ", " + c.j + ", " + c.finalCost + ", " + c.heuristicCost + "] -> ");
            }
            System.out.println();
        }
        if (!p.getChildren().contains(newP)) {
            Platform.runLater(() -> p.getChildren().add(newP));
        }
    }
    */
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
