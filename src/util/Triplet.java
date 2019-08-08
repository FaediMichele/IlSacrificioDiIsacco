package util;

import java.util.Objects;

/**
 * Tuple of three value.
 *
 * @param <X> value 1 type
 * @param <Y> value 2 type
 * @param <Z> value 3 type
 */
public class Triplet<X, Y, Z> {
    private final X x;
    private final Y y;
    private final Z z;

    /**
     * Create a tuple of three elements.
     * 
     * @param x element 1
     * @param y element 2
     * @param z element 3
     */
    public Triplet(final X x, final Y y, final Z z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Get The first value.
     * 
     * @return the first value
     */
    public X getX() {
        return this.x;
    }

    /**
     * Get the second value.
     * 
     * @return the second value
     */
    public Y getY() {
        return this.y;
    }

    /**
     * Get the third value.
     * 
     * @return the third value
     */
    public Z getZ() {
        return this.z;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    @SuppressWarnings("unchecked")
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
        final Triplet<Object, Object, Object> other = (Triplet<Object, Object, Object>) obj;
        return Objects.equals(this.z, other.z) && Objects.equals(this.y, other.y) && Objects.equals(this.z, other.z);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.getClass().getName() + " : " + this.x + " " + this.y + " " + this.z;
    }
}
