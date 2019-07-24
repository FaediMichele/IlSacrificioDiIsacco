package util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic interface for enums.
 */
public interface BasicEnum {
    /**
     * Gets the value.
     * 
     * @return the full path
     */
    default String getValue() {
        final Field[] fieldsEnum = this.getClass().getFields();
        final List<Field> listFields = Arrays.asList(fieldsEnum);
        final Field field = listFields.stream().filter(f -> {
            try {
                return f.get(this).equals(this);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList()).get(0);
        if (field == null) {
            return "";
        } else {
            return this.getClass().getName() + "." + field.getName();
        }
    }
}
