package test;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 *
 *
 */
@SuppressWarnings("all")
public class TestEnum {

    /**
     * 
     */
    @Test
    public void testExtandableEnum() {
        BasicStatusEnum enum1 = ExampleEnum.PROVA1, enum2 = Example2Enum.PROVA4;
        assertEquals(enum1.getValue(), "test.ExampleEnum.PROVA1");
    }

}
