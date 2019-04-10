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
    private final X v1;
    private final Y v2;
    private final Z v3;

    /**
     * Create a tuple of three elements.
     * @param x element 1
     * @param y element 2
     * @param z element 3
     */
    public Triplet(final X x, final Y y, final Z z) {
        this.v1 = x;
        this.v2 = y;
        this.v3 = z;
    }

    /**
     * Get The first value.
     * @return the first value
     */
    public X getV1() {
        return v1;
    }

    /**
     * Get the second value.
     * @return the second value
     */
    public Y getV2() {
        return v2;
    }

    /**
     * Get the third value.
     * @return the third value
     */
    public Z getV3() {
        return v3;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(v1, v2, v3);
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
        return Objects.equals(v1, other.v1) && Objects.equals(v2, other.v2)
            && Objects.equals(v3, other.v3);
    }
}
