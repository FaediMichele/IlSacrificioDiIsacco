package util;

import java.util.Objects;

/**
 * Tuple of two values.
 *
 * @param <X> X type
 * @param <Y> Y type
 */
public class Pair<X, Y> {
    private X x1;
    private Y y1;

    /**
     * Create a new Pair.
     * 
     * @param x key
     * @param y value
     */
    public Pair(final X x, final Y y) {
        this.x1 = x;
        this.y1 = y;
    }

    /**
     * Getter of the X.
     * 
     * @return the X
     */
    public X getX() {
        return x1;
    }

    /**
     * Setter of the X.
     * 
     * @param x the X to set
     */
    public void setX(final X x) {
        this.x1 = x;
    }

    /**
     * Getter of the Y.
     * 
     * @return the Y
     */
    public Y getY() {
        return y1;
    }

    /**
     * Setter of the Y.
     * 
     * @param y the Y to set
     */
    public void setY(final Y y) {
        this.y1 = y;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(x1, y1);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final Pair<X, Y> other = (Pair<X, Y>) obj;
        return Objects.equals(x1, other.x1) && Objects.equals(y1, other.y1);
    }

}
