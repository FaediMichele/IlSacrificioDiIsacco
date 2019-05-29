package util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for the fields that are not needed for the hashCode.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface NotHashCode {

}
