package util;

import java.util.function.Predicate;

/**
 * Two dimension array.
 *
 * @param <T> Type of elements
 */
public class Matrix<T> {
    private final int dimX;
    private final int dimY;
    private Object[][] mat;
    private int lastX = -1;
    private int lastY = -1;

    /**
     * Create a new mat with default value.
     * 
     * @param dimX         size of Row
     * @param dimY         size of Column
     * @param defaultValue Default value to store in the mat
     */
    public Matrix(final int dimX, final int dimY, final T defaultValue) {
        this.dimX = dimX;
        this.dimY = dimY;
        mat = new Object[dimX][dimY];
        if (defaultValue != null) {
            for (int i = 0; i < dimX; i++) {
                for (int j = 0; j < dimY; j++) {
                    mat[i][j] = defaultValue;
                }
            }
        }
    }

    /**
     * Create a new empty mat.
     * 
     * @param dimX size of Row
     * @param dimY size of Column
     */
    public Matrix(final int dimX, final int dimY) {
        this.dimX = dimX;
        this.dimY = dimY;
        mat = new Object[dimX][dimY];
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
                mat[i][j] = null;
            }
        }
    }

    /**
     * Get an element to a position.
     * 
     * @param posX Row index
     * @param posY Columns index
     * @return <T> element
     */
    @SuppressWarnings("unchecked")
    public T get(final int posX, final int posY) {
        lastX = posX;
        lastY = posY;
        return (T) mat[posX][posY];
    }

    /**
     * Set an element to a position.
     * 
     * @param posX    Row index
     * @param posY    Column index
     * @param element element to set
     */
    public void set(final int posX, final int posY, final T element) {
        lastX = posX;
        lastY = posY;
        mat[posX][posY] = element;
    }

    /**
     * Set any element that pass the predicate to a value.
     * @param element the value to set to the element.
     * @param p a function that say if an element will be override or not.
     */
    @SuppressWarnings("unchecked")
    public void setToPredicate(final T element, final Predicate<T> p) {
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
                if (p.test((T) mat[i][j])) {
                    mat[i][j] = element;
                }
            }
        }
    }
    /**
     * Get the last element setted or getted.
     * 
     * @return the last element used
     */
    @SuppressWarnings("unchecked")
    public T getLast() {
        if (lastX >= 0 && lastY >= 0) {
            return (T) mat[lastX][lastY];
        }
        return null;
    }

    /**
     * Get the dimension of the row.
     * 
     * @return the dimension of the row
     */
    public int getWidth() {
        return dimX;
    }

    /**
     * Get the dimension of the column.
     * 
     * @return the dimension of the column.
     */
    public int getHeight() {
        return dimY;
    }

    /**
     * Print the matrix in the default out.
     */
    public void test() {
        for (int i = 0; i < dimX; i++) {
            for (int j = 0; j < dimY; j++) {
                System.out.print((get(i, j) != null ? get(i, j) + " " : "[]") + " ");
            }
            System.out.println();
        }
    }
}
