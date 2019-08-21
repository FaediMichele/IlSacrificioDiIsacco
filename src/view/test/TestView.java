package view.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import model.enumeration.BasicPlayerEnum;
import model.enumeration.PlayerEnum;

/**
 * 
 * Test for view.
 *
 */
@SuppressWarnings("all")
public class TestView {
    /**
     * .
     * 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @SuppressWarnings({
            "unchecked", "rawtypes"
    })
    @Test
    public void testLoadEnumFromString() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        final String enumerationClass = "model.enumeration.BasicPlayerEnum";
        final String value = "ISAAC";
        final Enum<? extends PlayerEnum> enumObjet = Enum.valueOf((Class<Enum>) Class.forName(enumerationClass),
                value);
        assertEquals(BasicPlayerEnum.ISAAC, enumObjet);
    }
}
