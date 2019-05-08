package util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class for all static methods.
 */
public final class StaticMethodsUtils {
    private StaticMethodsUtils() {
    }

    /**
     * Generic static method for equals.
     * @param obj1 first Object
     * @param obj2 second Object
     * @return true if all the fields needed to check of the two objects are equals
     */
    public static boolean equals(final Object obj1, final Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        } else if (obj1 == null || obj2 == null) {
            return false;
        }

        if (obj1.getClass() != obj2.getClass()) {
            return false;
        }

        final Class<?> classObj1 = obj1.getClass();
        final Class<?> classObj2 = obj2.getClass();
        final List<Field> fieldsListObj1 = Stream.of(classObj1.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(NotEquals.class)).collect(Collectors.toList());
        final List<Field> fieldsListObj2 = Stream.of(classObj2.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(NotEquals.class)).collect(Collectors.toList());
        final List<Object> fieldsValueObj1 = fieldsListObj1.stream().flatMap(f -> {
            try {
                f.setAccessible(true);
                final Stream<Object> s = Stream.of(f.get(obj1));
                f.setAccessible(false);
                return s;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        final List<Object> fieldsValueObj2 = fieldsListObj2.stream().flatMap(f -> {
            try {
                f.setAccessible(true);
                final Stream<Object> s = Stream.of(f.get(obj2));
                f.setAccessible(false);
                return s;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        return fieldsValueObj1.equals(fieldsValueObj2);
    }

    /**
     * Generic static method for hashCode.
     * @param obj the Object
     * @return the hashCode
     */
    public static int hashCode(final Object obj) {
        final Class<?> classObj = obj.getClass();
        final List<Field> fieldsListObj = Stream.of(classObj.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(NotHashCode.class)).collect(Collectors.toList());
        final List<Object> fieldsValueObj = fieldsListObj.stream().flatMap(f -> {
            try {
                f.setAccessible(true);
                final Stream<Object> s = Stream.of(f.get(obj));
                f.setAccessible(false);
                return s;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return Arrays.hashCode(fieldsValueObj.toArray());
    }
}
